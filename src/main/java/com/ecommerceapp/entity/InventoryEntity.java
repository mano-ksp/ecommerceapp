package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "Inventory", schema = "Test", catalog = "")
public class InventoryEntity {
    private long id;
    private String name;
    private int quantityPerUnit;
    private String quantityMetric;
    private BigDecimal perUnitPrice;
    private int discount;
    private boolean available;
    private int availableUnits;
    private int version; // TODO use version to overcome race conditions

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return id;
    }


    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }


    @Basic
    @Column(name = "QuantityPerUnit")
    public int getQuantityPerUnit() {
        return quantityPerUnit;
    }


    @Basic
    @Column(name = "QuantityMetric")
    public String getQuantityMetric() {
        return quantityMetric;
    }


    @Basic
    @Column(name = "PerUnitPrice")
    public BigDecimal getPerUnitPrice() {
        return perUnitPrice;
    }


    @Basic
    @Column(name = "Discount")
    public int getDiscount() {
        return discount;
    }

    @Basic
    @Column(name = "Available")
    public boolean getAvailable() {
        return available;
    }

    @Basic
    @Column(name = "AvailableUnits")
    public int getAvailableUnits() {
        return availableUnits;
    }

    @Basic
    @Column(name = "Version")
    public int getVersion() {
        return version;
    }
}
