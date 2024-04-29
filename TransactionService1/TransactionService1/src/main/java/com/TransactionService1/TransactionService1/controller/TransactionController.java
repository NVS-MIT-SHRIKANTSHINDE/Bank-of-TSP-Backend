package com.TransactionService1.TransactionService1.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TransactionService1.TransactionService1.TransactionException.TransactionException;
import com.TransactionService1.TransactionService1.entity.Transaction;
import com.TransactionService1.TransactionService1.entity.TransferRequest;
import com.TransactionService1.TransactionService1.service.TransactionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferMoney(@RequestBody TransferRequest transferRequest) {
    	
    	
        try {
        	
            Transaction transaction = transactionService.transferMoney(transferRequest);
            
            return ResponseEntity.status(HttpStatus.SC_OK).body(transaction);
        } catch (TransactionException e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
        }
        
    }

    
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String accountId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }

}
