package com.TransactionService1.TransactionService1.entity;



import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TransferRequest {

    private String fromAccountId;
    private String toAccountId;
    private double amount;
    private String transactionPin;
	

    // Constructors, getters, and setters
}
