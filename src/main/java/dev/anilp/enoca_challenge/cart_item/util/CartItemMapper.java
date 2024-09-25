package dev.anilp.enoca_challenge.cart_item.util;

import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemResponseDto;

public class CartItemMapper {

    public static CartItemResponseDto cartItemToResponse(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getProduct().getName(),
                cartItem.getQuantity()
        );
    }
}
