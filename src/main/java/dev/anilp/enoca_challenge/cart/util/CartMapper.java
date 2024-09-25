package dev.anilp.enoca_challenge.cart.util;

import dev.anilp.enoca_challenge.cart.Cart;
import dev.anilp.enoca_challenge.cart.util.dto.CartResponseDto;
import dev.anilp.enoca_challenge.cart_item.util.CartItemMapper;

public class CartMapper {

    public static CartResponseDto cartToResponse(Cart cart) {
        return new CartResponseDto(
                cart.getId(),
                cart.getTotalAmount(),
                cart.getCustomer().getId(),
                cart.getCartItems().stream()
                        .map(CartItemMapper::cartItemToResponse)
                        .toList()
        );
    }
}
