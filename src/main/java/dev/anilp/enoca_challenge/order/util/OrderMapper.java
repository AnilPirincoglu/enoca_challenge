package dev.anilp.enoca_challenge.order.util;

import dev.anilp.enoca_challenge.order.Order;
import dev.anilp.enoca_challenge.order.util.dto.OrderResponseDto;
import dev.anilp.enoca_challenge.order_item.util.OrderItemMapper;
import dev.anilp.enoca_challenge.order_item.util.dto.OrderItemResponseDto;

import java.util.List;

public class OrderMapper {
    public static OrderResponseDto orderToResponse(Order order) {
        List<OrderItemResponseDto> list = order.getOrderItems().stream()
                .map(OrderItemMapper::orderItemToResponse)
                .toList();
        return new OrderResponseDto(list, order.getTotalAmount());
    }
}
