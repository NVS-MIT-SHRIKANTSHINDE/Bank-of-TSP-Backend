package com.TransactionService1.TransactionService1.entity;


import jakarta.persistence.Transient;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    private String transactionId;

    @DateTimeFormat
    private LocalDate transactionDate;

    private String description;

    private String status;

    private double amount;

    private String fromAccount;

    private String toAccount;
    

    // Constructors, getters, and setters


    private String encryptPin(String pin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pin);
    }


}
