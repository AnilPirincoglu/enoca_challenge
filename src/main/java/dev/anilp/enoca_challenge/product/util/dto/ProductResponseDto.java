package dev.anilp.enoca_challenge.product.util.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDto(UUID id, String name, BigDecimal price, Integer stockQuantity) {
}
