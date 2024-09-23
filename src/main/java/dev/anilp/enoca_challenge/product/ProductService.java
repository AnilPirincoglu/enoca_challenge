package dev.anilp.enoca_challenge.product;

import dev.anilp.enoca_challenge.exceptions.ResourceNotFoundException;
import dev.anilp.enoca_challenge.product.dto.AddProductRequestDto;
import dev.anilp.enoca_challenge.product.dto.ProductResponseDto;
import dev.anilp.enoca_challenge.product.dto.UpdateProductRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static dev.anilp.enoca_challenge.exceptions.ErrorMessage.PRODUCT_ALREADY_EXISTS;
import static dev.anilp.enoca_challenge.exceptions.ErrorMessage.PRODUCT_NOT_FOUND_WITH_GIVEN_ID;
import static dev.anilp.enoca_challenge.product.ProductMapper.addProductRequestToProduct;
import static dev.anilp.enoca_challenge.product.ProductMapper.productToResponse;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::productToResponse)
                .toList();
    }

    public ProductResponseDto getProductById(UUID id) {
        return productToResponse(findProductById(id));
    }

    public void addProduct(AddProductRequestDto addProductRequestDTO) {
        checkProductExistsByName(addProductRequestDTO.name());

        log.info("Adding product: {}", addProductRequestDTO);
        productRepository.save(
                addProductRequestToProduct(addProductRequestDTO));
    }

    public ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto updateProductRequestDTO) {
        checkProductExistsByName(updateProductRequestDTO.name());
        Product product = findProductById(id);

        log.info("Updating product: {} to {}", product, updateProductRequestDTO);
        product.setName(updateProductRequestDTO.name());
        product.setPrice(updateProductRequestDTO.price());
        product.setStockQuantity(updateProductRequestDTO.stockQuantity());

        return productToResponse(productRepository.save(product));
    }

    public void deleteProduct(UUID id) {
        Product product = findProductById(id);

        log.info("Deleting product: {}", product);
        productRepository.delete(product);
    }

    private Product findProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_GIVEN_ID, id.toString()));
    }

    private void checkProductExistsByName(String name) {
        if (productRepository.existsProductByName(name)) {
            throw new ResourceNotFoundException(PRODUCT_ALREADY_EXISTS, name);
        }
    }
}

