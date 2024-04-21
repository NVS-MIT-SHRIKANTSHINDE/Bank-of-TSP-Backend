package com.TransactionService1.TransactionService1.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Account {

    @Id
    private String accountId;

    private String accountType;

    @DateTimeFormat
    private Date accountOpeningDate;

    @DateTimeFormat
    private Date lastActivity;

    private double balance;

    private String customerId;

    private String transactionPin;

    private String accountBranch;

    // Getters and setters
}
