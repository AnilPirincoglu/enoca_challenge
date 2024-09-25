package dev.anilp.enoca_challenge.product.util;

import dev.anilp.enoca_challenge.product.Product;
import dev.anilp.enoca_challenge.product.util.dto.AddProductRequestDto;
import dev.anilp.enoca_challenge.product.util.dto.ProductResponseDto;

public class ProductMapper {

    public static ProductResponseDto productToResponse(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity());
    }

    public static Product addProductRequestToProduct(AddProductRequestDto addProductRequestDto) {
        return new Product(addProductRequestDto.name(), addProductRequestDto.price(), addProductRequestDto.stockQuantity());
    }
}
