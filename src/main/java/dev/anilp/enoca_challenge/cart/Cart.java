package dev.anilp.enoca_challenge.cart;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.cartItem.CartItem;
import dev.anilp.enoca_challenge.customer.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Cart")
@Table(name = "cart")
public class Cart extends BaseEntity {

    @Id
    @SequenceGenerator(name = "cart_sequence", sequenceName = "cart_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    private Long id;

    @Column(name = "total_amount", nullable = false, columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "cart_customer_id_fk"))
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
        this.totalAmount = BigDecimal.ZERO;
    }

    public Cart(Long id, BigDecimal totalAmount, Customer customer) {
        this();
        this.id = id;
        this.totalAmount = totalAmount;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, cart.id) && Objects.equals(totalAmount, cart.totalAmount) && Objects.equals(customer, cart.customer) && Objects.equals(cartItems, cart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, totalAmount, customer, cartItems);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", customer=" + customer +
                ", cartItems=" + cartItems +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}