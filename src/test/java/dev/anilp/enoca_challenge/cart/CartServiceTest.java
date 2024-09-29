package dev.anilp.enoca_challenge.cart;

import dev.anilp.enoca_challenge.cart.util.dto.AddProductToCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.CartResponseDto;
import dev.anilp.enoca_challenge.cart.util.dto.RemoveProductFromCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.UpdateCartRequestDto;
import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.cart_item.CartItemId;
import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemRequestDto;
import dev.anilp.enoca_challenge.customer.Customer;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
import dev.anilp.enoca_challenge.product.Product;
import dev.anilp.enoca_challenge.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCart_returnsCartResponseDto_whenCartExists() {
        Long cartId = 1L;
        Customer customer = new Customer();

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        CartResponseDto response = cartService.getCart(cartId);

        assertNotNull(response);
        verify(cartRepository).findById(cartId);
    }

    @Test
    void getCart_throwsResourceNotFoundException_whenCartDoesNotExist() {
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getCart(cartId));
        verify(cartRepository).findById(cartId);
    }

    @Test
    void addProductToCart_addsNewItem_whenCartIsEmpty() {
        UUID customerId = UUID.randomUUID();
        AddProductToCartRequestDto requestDto = new AddProductToCartRequestDto(customerId, "Product1");

        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);

        Cart cart = new Cart();

        when(productService.findProductByName("Product1")).thenReturn(product);
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.addProductToCart(requestDto, 2);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(2, cart.getCartItems().getFirst().getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void addProductToCart_addsNewItem_whenCartContainsOtherProducts() {
        UUID customerId = UUID.randomUUID();
        AddProductToCartRequestDto requestDto = new AddProductToCartRequestDto(customerId, "Product1");

        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(BigDecimal.TEN);

        Cart cart = new Cart();
        CartItem existingItem = new CartItem();
        existingItem.setProduct(product2);
        existingItem.setQuantity(1);
        existingItem.calculateTotalAmount();
        cart.getCartItems().add(existingItem);

        when(productService.findProductByName("Product1")).thenReturn(product);
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.addProductToCart(requestDto, 2);

        assertEquals(2, cart.getCartItems().size());
        assertEquals(2, cart.getCartItems().getLast().getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void addProductToCart_updatesQuantity_whenProductInCart() {
        UUID customerId = UUID.randomUUID();
        AddProductToCartRequestDto requestDto = new AddProductToCartRequestDto(customerId, "Product1");
        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(new CartItemId(cart.getId(), product.getId()), 1, cart, product);
        cart.getCartItems().add(cartItem);
        when(productService.findProductByName("Product1")).thenReturn(product);
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.addProductToCart(requestDto, 2);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(3, cartItem.getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void updateCart_updatesCartItems() {
        Long cartId = 1L;
        UpdateCartRequestDto requestDto = new UpdateCartRequestDto(List.of(new CartItemRequestDto("Product1", 2)));
        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        Cart cart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productService.findProductByName("Product1")).thenReturn(product);

        cartService.updateCart(cartId, requestDto);

        assertEquals(1, cart.getCartItems().size());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeProductFromCart_removesItem_whenProductInCart() {
        UUID customerId = UUID.randomUUID();
        RemoveProductFromCartRequestDto requestDto = new RemoveProductFromCartRequestDto(customerId);
        Product product = new Product();
        product.setName("Product1");
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(new CartItemId(cart.getId(), product.getId()), 1, cart, product);
        cart.getCartItems().add(cartItem);
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.removeProductFromCart("Product1", requestDto);

        assertTrue(cart.getCartItems().isEmpty());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeProductFromCart_throwsResourceNotFoundException_whenProductNotInCart() {
        UUID customerId = UUID.randomUUID();
        RemoveProductFromCartRequestDto requestDto = new RemoveProductFromCartRequestDto(customerId);
        Cart cart = new Cart();
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        assertThrows(ResourceNotFoundException.class, () -> cartService.removeProductFromCart("Product1", requestDto));
    }

    @Test
    void emptyCart_clearsAllItems_whenCartExists() {
        Long cartId = 1L;
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(new CartItemId(cart.getId(), UUID.randomUUID()), 1, cart, new Product());
        cart.getCartItems().add(cartItem);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        cartService.emptyCart(cartId);

        assertTrue(cart.getCartItems().isEmpty());
        verify(cartRepository).save(cart);
    }
}