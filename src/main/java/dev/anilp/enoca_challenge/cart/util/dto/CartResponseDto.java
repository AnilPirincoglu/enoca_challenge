package dev.anilp.enoca_challenge.cart.util.dto;

import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartResponseDto(
        Long id,
        BigDecimal totalAmount,
        UUID customerId,
        List<CartItemResponseDto> cartItems
) {
}
