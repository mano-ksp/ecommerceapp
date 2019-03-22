package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "OrderDetails", schema = "Test")
public class OrderDetailsEntity {
    private Long id;
    private UUID orderId;
    private Long productId;
    private BigDecimal price;
    private int quantity;
    private Integer discount;
    private Timestamp created;
    private Timestamp modified;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "OrderID")
    public UUID getOrderId() {
        return orderId;
    }

    @Basic
    @Column(name = "ProductID")
    public Long getProductId() {
        return productId;
    }

    @Basic
    @Column(name = "Price")
    public BigDecimal getPrice() {
        return price;
    }

    @Basic
    @Column(name = "Quantity")
    public int getQuantity() {
        return quantity;
    }

    @Basic
    @Column(name = "Discount")
    public Integer getDiscount() {
        return discount;
    }

    @Basic
    @Column(name = "Created")
    public Timestamp getCreated() {
        return created;
    }

    @Basic
    @Column(name = "Modified")
    public Timestamp getModified() {
        return modified;
    }
}
