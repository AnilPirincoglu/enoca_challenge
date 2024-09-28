package dev.anilp.enoca_challenge.cart.util.dto;

import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemRequestDto;

import java.util.List;

public record UpdateCartRequestDto(
        List<CartItemRequestDto> cartItems
) {
}
