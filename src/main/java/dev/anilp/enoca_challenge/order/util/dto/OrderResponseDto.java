package dev.anilp.enoca_challenge.order.util.dto;

import dev.anilp.enoca_challenge.order_item.util.dto.OrderItemResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(List<OrderItemResponseDto> orderItems, BigDecimal totalAmount) {
}
