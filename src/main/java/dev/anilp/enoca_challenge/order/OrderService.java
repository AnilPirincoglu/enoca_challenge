package dev.anilp.enoca_challenge.order;

import dev.anilp.enoca_challenge.cart.Cart;
import dev.anilp.enoca_challenge.cart.CartService;
import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
import dev.anilp.enoca_challenge.order.util.OrderMapper;
import dev.anilp.enoca_challenge.order.util.dto.OrderResponseDto;
import dev.anilp.enoca_challenge.order_item.OrderItem;
import dev.anilp.enoca_challenge.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static dev.anilp.enoca_challenge.exception.ErrorMessage.ORDER_NOT_FOUND_WITH_GIVEN_CODE;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Transactional
    public void PlaceOrder(Long cartId) {
        Cart cart = cartService.findCartById(cartId);
        Order order = new Order(cart.getCustomer());

        cart.getCartItems()
                .forEach(cartItem -> order.getOrderItems().add(newOrderItem(cartItem, order)));
        order.calculateTotalAmount();
        orderRepository.save(order);
        cartService.emptyCart(cartId);
    }

    private OrderItem newOrderItem(CartItem cartItem, Order order) {
        productService.checkStockQuantity(cartItem.getProduct(), cartItem.getQuantity());
        productService.decreaseStockQuantity(cartItem.getProduct(), cartItem.getQuantity());
        OrderItem orderItem = new OrderItem(
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity(),
                order);
        orderItem.calculateTotalAmount();
        return orderItem;
    }

    public OrderResponseDto getOrderByCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .map(OrderMapper::orderToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND_WITH_GIVEN_CODE, orderCode));
    }

    public List<OrderResponseDto> getOrdersForCustomer(UUID customerId) {
        return orderRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(OrderMapper::orderToResponse)
                .toList();
    }
}
