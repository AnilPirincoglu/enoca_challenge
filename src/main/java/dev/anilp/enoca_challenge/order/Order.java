package dev.anilp.enoca_challenge.order;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.customer.Customer;
import dev.anilp.enoca_challenge.order_item.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Order")
@Table(name = "\"order\"", uniqueConstraints = {@UniqueConstraint(name = "order_order_code_unique", columnNames = "order_code")})
public class Order extends BaseEntity {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long id;
    @Column(name = "order_code", nullable = false, columnDefinition = "varchar(25)")
    private String orderCode;
    @Column(name = "total_amount", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal totalAmount;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "order_customer_id_fk"))
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @SuppressWarnings("unused")
    public Order() {
        this.orderCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.totalAmount = BigDecimal.ZERO;
    }

    public Order(Customer customer) {
        this();
        this.customer = customer;
    }

    @SuppressWarnings("unused")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getOrderCode() {
        return orderCode;
    }

    @SuppressWarnings("unused")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @SuppressWarnings("unused")
    public Customer getCustomer() {
        return customer;
    }

    @SuppressWarnings("unused")
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .map(OrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order order)) {
            return false;
        }
        return Objects.equals(id, order.id) && Objects.equals(orderCode, order.orderCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderCode);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", totalAmount=" + totalAmount +
                ", customer=" + customer.getId() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
