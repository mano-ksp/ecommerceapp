package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "Account", schema = "Test")
public class AccountEntity {
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String country;
    private Integer phone;
    private Integer mobile;
    private String email;
    private int discount;
    private String userType;
    private Timestamp modified;
    private Timestamp created;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    @Basic
    @Column(name = "State")
    public String getState() {
        return state;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    @Basic
    @Column(name = "Phone")
    public Integer getPhone() {
        return phone;
    }

    @Basic
    @Column(name = "Mobile")
    public Integer getMobile() {
        return mobile;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    @Basic
    @Column(name = "Discount")
    public int getDiscount() {
        return discount;
    }

    @Basic
    @Column(name = "UserType")
    public String getUserType() {
        return userType;
    }

    @Basic
    @Column(name = "Modified")
    public Timestamp getModified() {
        return modified;
    }

    @Basic
    @Column(name = "Created")
    public Timestamp getCreated() {
        return created;
    }

}
