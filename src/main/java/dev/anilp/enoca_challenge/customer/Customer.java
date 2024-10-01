package dev.anilp.enoca_challenge.customer;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.cart.Cart;
import dev.anilp.enoca_challenge.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Customer")
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(name = "customer_email_unique", columnNames = "email"))
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(100)")
    private String lastName;

    @Email(message = "Email should be valid")
    @Size(max = 150, message = "Email must be less than 150 characters")
    @Column(name = "email", nullable = false, columnDefinition = "varchar(150)")
    private String email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    @SuppressWarnings("unused")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        if (cart.getCustomer() != this) {
            cart.setCustomer(this);
        }
    }

    public Cart addCart() {
        cart = new Cart(this);
        this.setCart(cart);
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer customer)) {
            return false;
        }
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(cart, customer.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, email, cart);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cart=" + cart.getCartItems() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
