package dev.anilp.enoca_challenge.order_item.util;

import dev.anilp.enoca_challenge.order_item.OrderItem;
import dev.anilp.enoca_challenge.order_item.util.dto.OrderItemResponseDto;

public class OrderItemMapper {
    public static OrderItemResponseDto orderItemToResponse(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getTotalAmount()
        );
    }
}
