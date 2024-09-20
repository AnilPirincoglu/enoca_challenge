package dev.anilp.enoca_challenge.product;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.cartItem.CartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Product")
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "decimal", precision = 8, scale = 2)
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false, columnDefinition = "integer")
    @Min(value = 1, message = "Stock quantity must be greater than 0")
    private int stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public Product() {
    }

    public Product(String name, BigDecimal price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(UUID id, String name, BigDecimal price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name) {
        this.name = name;
    }

    public @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal price) {
        this.price = price;
    }

    @Min(value = 1, message = "Stock quantity must be greater than 0")
    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(@Min(value = 1, message = "Stock quantity must be greater than 0") int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        if (!super.equals(o)) return false;
        return stockQuantity == product.stockQuantity && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(cartItems, product.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, price, stockQuantity, cartItems);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", cartItems=" + cartItems +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
