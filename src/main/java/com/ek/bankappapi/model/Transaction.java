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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String fromAccount;
    private String toAccount;

    @Column(nullable = false)
    private Double transactionAmount;

    @Column(nullable = false)
    private String transactionType; // DEPOSIT, WITHDRAW, TRANSFER

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();

    public Transaction(String fromAccount, String toAccount, Double transactionAmount,
                       String transactionType, Date transactionDate) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }
}