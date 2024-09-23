package dev.anilp.enoca_challenge.product;

import dev.anilp.enoca_challenge.product.dto.AddProductRequestDto;
import dev.anilp.enoca_challenge.product.dto.ProductResponseDto;

public class ProductMapper {

    public static ProductResponseDto productToResponse(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity());
    }

    public static Product addProductRequestToProduct(AddProductRequestDto addProductRequestDto) {
        return new Product(addProductRequestDto.name(), addProductRequestDto.price(), addProductRequestDto.stockQuantity());
    }
}
