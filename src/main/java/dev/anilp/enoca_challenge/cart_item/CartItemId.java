package dev.anilp.enoca_challenge.cart_item;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CartItemId implements Serializable {
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private UUID productId;

    public CartItemId() {
    }

    public CartItemId(Long cartId, UUID productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItemId that)) {
            return false;
        }
        return Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }

    @Override
    public String toString() {
        return "CartItemId{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                '}';
    }
}
