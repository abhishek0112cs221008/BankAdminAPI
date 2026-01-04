package com.ek.bankappapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String email;

    private String customerPhone;
    private String customerAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public Customer(String customerName, String email, String customerPhone, String customerAddress) {
        this.customerName = customerName;
        this.email = email;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
    }
}