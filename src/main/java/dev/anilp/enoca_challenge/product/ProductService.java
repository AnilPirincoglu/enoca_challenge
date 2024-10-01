package dev.anilp.enoca_challenge.product;

import dev.anilp.enoca_challenge.exception.exceptions.InsufficientStockException;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
import dev.anilp.enoca_challenge.product.util.ProductMapper;
import dev.anilp.enoca_challenge.product.util.dto.AddProductRequestDto;
import dev.anilp.enoca_challenge.product.util.dto.ProductResponseDto;
import dev.anilp.enoca_challenge.product.util.dto.UpdateProductRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static dev.anilp.enoca_challenge.exception.ErrorMessage.INSUFFICIENT_STOCK;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.PRODUCT_ALREADY_EXISTS;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.PRODUCT_NOT_FOUND_WITH_GIVEN_ID;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.PRODUCT_NOT_FOUND_WITH_GIVEN_NAME;
import static dev.anilp.enoca_challenge.product.util.ProductMapper.addProductRequestToProduct;
import static dev.anilp.enoca_challenge.product.util.ProductMapper.productToResponse;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

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

    public void updateProduct(UUID id, UpdateProductRequestDto updateProductRequestDTO) {
        Product product = findProductById(id);
        if (!Objects.equals(product.getName(), updateProductRequestDTO.name())) {
            checkProductExistsByName(updateProductRequestDTO.name());
            log.info("Product name not changed.");
            product.setName(updateProductRequestDTO.name());
        }
        log.info("Updating product: {}", updateProductRequestDTO);
        product.setPrice(updateProductRequestDTO.price());
        product.setStockQuantity(updateProductRequestDTO.stockQuantity());
        productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        Product product = findProductById(id);

        log.info("Deleting product: {}", product);
        productRepository.delete(product);
    }

    public void decreaseStockQuantity(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    public void checkStockQuantity(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(INSUFFICIENT_STOCK, product.getStockQuantity());
        }
    }

    public Product findProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_GIVEN_NAME, name));
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

