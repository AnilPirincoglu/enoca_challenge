package dev.anilp.enoca_challenge.cart;

import dev.anilp.enoca_challenge.cart.util.dto.AddProductToCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.CartResponseDto;
import dev.anilp.enoca_challenge.cart.util.dto.RemoveProductFromCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.UpdateCartRequestDto;
import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.cart_item.CartItemId;
import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemRequestDto;
import dev.anilp.enoca_challenge.customer.CustomerService;
import dev.anilp.enoca_challenge.exception.ErrorMessage;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
import dev.anilp.enoca_challenge.product.Product;
import dev.anilp.enoca_challenge.product.ProductService;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static dev.anilp.enoca_challenge.cart.util.CartMapper.cartToResponse;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.CART_NOT_FOUND_WITH_GIVEN_ID;

@Service
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    public CartResponseDto getCart(Long cartId) {
        return cartToResponse(findCartById(cartId));
    }

    @Transactional
    public void addProductToCart(AddProductToCartRequestDto requestDto, @Positive(message = "Quantity must be a positive integer.") Integer quantity) {
        Cart cart = getCustomerCart(requestDto.customerId());

        Product product = productService.findProductByName(requestDto.productName());
        productService.checkStockQuantity(product, quantity);

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals(requestDto.productName()))
                .findFirst()
                .ifPresentOrElse(cartItem -> updateItemQuantity(cart, cartItem, quantity),
                        () -> addNewItemToCart(cart, product, quantity));

        cart.calculateTotalAmount();
        cartRepository.save(cart);
    }

    private void updateItemQuantity(Cart cart, CartItem cartItem, Integer quantity) {
        log.info("Updating quantity of cart item {} in cart with id: {}", cartItem.getProduct().getName(), cart.getId());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.calculateTotalAmount();
        log.info("Cart item updated: {}, total amount: {}", cartItem.getProduct().getName(), cartItem.getTotalAmount());
    }

    private void addNewItemToCart(Cart cart, Product product, Integer quantity) {
        log.info("Adding product {} to cart with id: {}", product.getName(), cart.getId());
        cart.getCartItems().add(newCartItem(cart, product, quantity));
    }

    @Transactional
    public void updateCart(Long cartId, UpdateCartRequestDto requestDto) {
        Cart cart = findCartById(cartId);

        List<CartItem> cartItems = requestDto.cartItems().stream()
                .map(request -> createNewCartItem(request, cart))
                .toList();

        log.info("Updating cart with id: {}", cartId);
        cart.getCartItems().clear();
        cart.getCartItems().addAll(cartItems);
        cart.calculateTotalAmount();
        cartRepository.save(cart);
    }

    private CartItem createNewCartItem(CartItemRequestDto request, Cart cart) {
        Product product = productService.findProductByName(request.productName());
        productService.checkStockQuantity(product, request.quantity());
        return newCartItem(cart, product, request.quantity());
    }

    @Transactional
    public void removeProductFromCart(String productName, RemoveProductFromCartRequestDto request) {
        Cart cart = getCustomerCart(request.customerId());

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst()
                .ifPresentOrElse(cartItem -> removeItemFormCart(cart, cartItem),
                        () -> {
                            throw new ResourceNotFoundException(ErrorMessage.PRODUCT_NOT_IN_CART, productName);
                        });
    }

    private void removeItemFormCart(Cart cart, CartItem cartItem) {
        log.info("Removing product {} from cart with id: {}", cartItem.getProduct().getName(), cart.getId());
        cart.getCartItems().remove(cartItem);
        cart.calculateTotalAmount();
        cartRepository.save(cart);
    }

    @Transactional
    public void emptyCart(Long cartId) {
        Cart cart = findCartById(cartId);
        log.info("Emptying cart with id: {}", cartId);
        cart.getCartItems().clear();
        cart.calculateTotalAmount();
        cartRepository.save(cart);
    }

    private CartItem newCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem(new CartItemId(cart.getId(), product.getId()), quantity, cart, product);
        cartItem.calculateTotalAmount();
        log.info("Cart item created for product: {}, total amount: {}", product.getName(), cartItem.getTotalAmount());
        return cartItem;
    }

    private Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(CART_NOT_FOUND_WITH_GIVEN_ID, cartId.toString()));
    }

    private Cart getCustomerCart(UUID customerId) {
        return cartRepository.findCartByCustomerId(customerId)
                .orElseGet(() -> customerService.findCustomerById(customerId).addCart());
    }
}