package dev.anilp.enoca_challenge.cart_item.util.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CartItemRequestDto(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String productName,
        @Positive(message = "The quantity must be a positive integer.")
        Integer quantity
) {
}
