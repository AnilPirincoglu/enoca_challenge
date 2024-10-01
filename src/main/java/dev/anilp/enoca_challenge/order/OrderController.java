package dev.anilp.enoca_challenge.order;

import dev.anilp.enoca_challenge.order.util.dto.OrderResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("customer/{customerId}")
    public List<OrderResponseDto> getAllOrdersForCustomer(@PathVariable UUID customerId) {
        return orderService.getOrdersForCustomer(customerId);
    }

    @GetMapping("code/{orderCode}")
    public OrderResponseDto getOrder(@PathVariable String orderCode) {
        return orderService.getOrderByCode(orderCode);
    }

    @PostMapping("{cartId}")
    public void placeOrder(@PathVariable Long cartId) {
        orderService.PlaceOrder(cartId);
    }
}
