package dev.anilp.enoca_challenge.cart_item;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.cart.Cart;
import dev.anilp.enoca_challenge.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "CartItem")
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    private static final Logger log = LoggerFactory.getLogger(CartItem.class);

    @EmbeddedId
    private CartItemId id;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer")
    private int quantity;

    @Column(name = "total_price", nullable = false, columnDefinition = "numeric")
    private BigDecimal totalAmount;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "cart_item_cart_id_fk"))
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "cart_item_product_id_fk"))
    private Product product;

    public CartItem() {
    }

    public CartItem(CartItemId id, int quantity, Cart cart, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotalAmount() {
        BigDecimal price = product.getPrice();
        this.totalAmount = price.multiply(BigDecimal.valueOf(this.quantity));
        log.info("Total amount calculated for product: {}, total amount: {}", this.product.getName(), this.totalAmount);
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItem cartItem)) {
            return false;
        }
        return Objects.equals(id, cartItem.id) && Objects.equals(cart, cartItem.cart) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, product);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", cart=" + cart.getId() +
                ", product=" + product.getName() +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
