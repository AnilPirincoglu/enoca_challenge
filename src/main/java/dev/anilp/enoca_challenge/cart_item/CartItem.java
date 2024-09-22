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

import java.util.Objects;

@Entity(name = "CartItem")
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @EmbeddedId
    private CartItemId id;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer")
    private int quantity;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "cart_item_cart_id_fk"))
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "cart_item_product_id_fk"))
    private Product product;

    public CartItem() {
        this.quantity = 1;
    }

    public CartItem(CartItemId id, Cart cart, Product product) {
        this();
        this.id = id;
        this.cart = cart;
        this.product = product;
    }

    public CartItemId getId() {
        return id;
    }

    public void setId(CartItemId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem cartItem)) return false;
        if (!super.equals(o)) return false;
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(cart, cartItem.cart) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, quantity, cart, product);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", cart=" + cart +
                ", product=" + product +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
