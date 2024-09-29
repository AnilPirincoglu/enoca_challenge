package dev.anilp.enoca_challenge.cart;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.cart_item.CartItem;
import dev.anilp.enoca_challenge.customer.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Cart")
@Table(name = "cart")
public class Cart extends BaseEntity {

    private static final Logger log = LoggerFactory.getLogger(Cart.class);

    @Id
    @SequenceGenerator(name = "cart_sequence", sequenceName = "cart_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "total_amount", nullable = false, columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "cart_customer_id_fk"))
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (Objects.equals(customer.getCart(), this)) {
            customer.setCart(this);
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotalAmount() {
        this.totalAmount = cartItems.stream()
                .map(CartItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Calculated cart {} total amount: {}", this.getId(), this.totalAmount);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.totalAmount = BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart cart)) {
            return false;
        }
        return Objects.equals(id, cart.id) && Objects.equals(totalAmount, cart.totalAmount) && Objects.equals(customer, cart.customer) && Objects.equals(cartItems, cart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalAmount, customer, cartItems);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", customer=" + customer.getName() +
                ", cartItems=" + cartItems +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}