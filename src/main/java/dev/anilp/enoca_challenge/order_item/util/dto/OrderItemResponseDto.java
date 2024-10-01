package dev.anilp.enoca_challenge.order_item.util.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(String productName, BigDecimal price, int quantity, BigDecimal totalAmount) {
}
