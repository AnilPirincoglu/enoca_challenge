package dev.anilp.enoca_challenge.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
        String name,

        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        @Column(name = "price", nullable = false, columnDefinition = "decimal", precision = 8, scale = 2)
        BigDecimal price,

        @Min(value = 1, message = "Stock quantity must be greater than 0")
        @Column(name = "stock_quantity", nullable = false, columnDefinition = "integer")
        Integer stockQuantity) {
}
