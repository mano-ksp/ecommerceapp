package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "Payment", schema = "Test")
public class PaymentEntity {
    private long id;
    private UUID orderId;
    private String type;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Basic
    @Column(name = "OrderID")
    public UUID getOrderId() {
        return orderId;
    }

    @Basic
    @Column(name = "Type")
    public String getType() {
        return type;
    }
}
