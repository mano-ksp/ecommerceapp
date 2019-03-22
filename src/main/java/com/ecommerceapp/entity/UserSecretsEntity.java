package com.ecommerceapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data // Will generate default setters and equals & hashcode
@Entity
@Table(name = "UserSecrets", schema = "Test")
public class UserSecretsEntity {
    private Long id;
    private Long accountId;
    private String userName;
    private byte[] password;
    private String creditCard;
    private String debitCard;
    private Timestamp modified;
    private Timestamp created;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "AccountID")
    public Long getAccountId() {
        return accountId;
    }

    @Basic
    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    @Basic
    @Column(name = "Password")
    public byte[] getPassword() {
        return password;
    }

    @Basic
    @Column(name = "CreditCard")
    public String getCreditCard() {
        return creditCard;
    }

    @Basic
    @Column(name = "DebitCard")
    public String getDebitCard() {
        return debitCard;
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
