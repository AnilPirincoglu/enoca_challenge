package dev.anilp.enoca_challenge.cart.util.dto;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddProductToCartRequestDto(
        UUID customerId,
        @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
        String productName) {
}
