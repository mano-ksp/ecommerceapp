package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "Orders", schema = "Test")
public class OrdersEntity {
    private UUID id;
    private long accountId;
    private Long paymentId;
    private Timestamp orderDate;
    private String txStatus;
    private String errorMsg;
    private boolean paid;
    private Timestamp paidDate;
    private Timestamp shipDate;
    private Timestamp created;
    private Timestamp modified;

    @Id
    @Column(name = "ID")
    public UUID getId() {
        return id;
    }

    @Basic
    @Column(name = "AccountID")
    public long getAccountId() {
        return accountId;
    }

    @Basic
    @Column(name = "PaymentID")
    public Long getPaymentId() {
        return paymentId;
    }

    @Basic
    @Column(name = "OrderDate")
    public Timestamp getOrderDate() {
        return orderDate;
    }

    @Basic
    @Column(name = "TxStatus")
    public String getTxStatus() {
        return txStatus;
    }

    @Basic
    @Column(name = "ErrorMsg")
    public String getErrorMsg() {
        return errorMsg;
    }

    @Basic
    @Column(name = "Paid")
    public boolean getPaid() {
        return paid;
    }

    @Basic
    @Column(name = "PaidDate")
    public Timestamp getPaidDate() {
        return paidDate;
    }

    @Basic
    @Column(name = "ShipDate")
    public Timestamp getShipDate() {
        return shipDate;
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
