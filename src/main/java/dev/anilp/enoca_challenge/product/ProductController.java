package dev.anilp.enoca_challenge.product;

import dev.anilp.enoca_challenge.product.util.dto.AddProductRequestDto;
import dev.anilp.enoca_challenge.product.util.dto.ProductResponseDto;
import dev.anilp.enoca_challenge.product.util.dto.UpdateProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDto> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public ProductResponseDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody AddProductRequestDto addProductRequestDTO) {
        productService.addProduct(addProductRequestDTO);
    }

    @PutMapping("{id}")
    public void updateProduct(@PathVariable UUID id, @Valid @RequestBody UpdateProductRequestDto updateProductRequestDTO) {
        productService.updateProduct(id, updateProductRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
