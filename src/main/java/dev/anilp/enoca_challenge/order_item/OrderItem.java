package dev.anilp.enoca_challenge.order_item;

import dev.anilp.enoca_challenge.BaseEntity;
import dev.anilp.enoca_challenge.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
    private Long id;
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;
    @DecimalMin(value = "1", message = "Price must be greater than 0")
    @Column(name = "price", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal price;
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "total_amount", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "order_item_order_id_fk"))
    private Order order;

    public OrderItem() {
    }

    public OrderItem(String name, BigDecimal price, Integer quantity, Order order) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    @SuppressWarnings("unused")
    public OrderItem(Long id, String name, BigDecimal price, Integer quantity, Order order) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public @DecimalMin(value = "1", message = "Price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(@DecimalMin(value = "1", message = "Price must be greater than 0") BigDecimal price) {
        this.price = price;
    }

    public @Min(value = 1, message = "Quantity must be greater than 0") Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotalAmount() {
        this.totalAmount = price.multiply(BigDecimal.valueOf(quantity));
    }

    @SuppressWarnings("unused")
    public Order getOrder() {
        return order;
    }

    @SuppressWarnings("unused")
    public void setName(@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public void setQuantity(@Min(value = 1, message = "Quantity must be greater than 0") Integer quantity) {
        this.quantity = quantity;
    }

    @SuppressWarnings("unused")
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", order=" + order.getId() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
