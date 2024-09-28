package dev.anilp.enoca_challenge.cart_item.util;

import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.cart_item.util.dto.CartItemRequestDto;

public class CartItemMapper {

    public static CartItemRequestDto cartItemToResponse(CartItem cartItem) {
        return new CartItemRequestDto(
                cartItem.getProduct().getName(),
                cartItem.getQuantity()
        );
    }
}
