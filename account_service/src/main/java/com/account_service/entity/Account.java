package com.account_service.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Accounts")
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

    // New fields
    private String transactionPin;

    private String accountBranch;

    // Getters and setters

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        // Perform encryption before setting the transaction pin
        this.transactionPin = encryptPin(transactionPin);
    }

    public String getAccountBranch() {
        return accountBranch;
    }

    public void setAccountBranch(String accountBranch) {
        this.accountBranch = accountBranch;
    }

    // Helper method for encryption
    private String encryptPin(String pin) {
    	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pin);
    }

    @Transient
    Customer customer;
}
