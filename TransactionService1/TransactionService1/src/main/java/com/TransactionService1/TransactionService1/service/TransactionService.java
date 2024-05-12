package com.TransactionService1.TransactionService1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.TransactionService1.TransactionService1.TransactionException.TransactionException;
import com.TransactionService1.TransactionService1.entity.Account;
import com.TransactionService1.TransactionService1.entity.Transaction;
import com.TransactionService1.TransactionService1.entity.TransferRequest;
import com.TransactionService1.TransactionService1.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;


    @Transactional
    public Transaction transferMoney(TransferRequest transferRequest) throws TransactionException {
        Account fromAccount = accountService.getAccountById(transferRequest.getFromAccountId());
        Account toAccount = accountService.getAccountById(transferRequest.getToAccountId());
        
        if (fromAccount == null || toAccount == null) {
            throw new TransactionException("Invalid account(s)");
        }

        if (!validateTransactionPin(fromAccount, transferRequest.getTransactionPin())) {
            throw new TransactionException("Invalid transaction PIN");
        }

        if (fromAccount.getBalance() < transferRequest.getAmount()) {
            throw new TransactionException("Insufficient balance");
        }

        try {
            fromAccount.setBalance(fromAccount.getBalance() - transferRequest.getAmount());
            toAccount.setBalance(toAccount.getBalance() + transferRequest.getAmount());


            accountService.updateAccount(fromAccount);
            accountService.updateAccount(toAccount);

          Transaction transaction=new Transaction();
          transaction.setTransactionDate(LocalDate.now());
          transaction.setTransactionId(UUID.randomUUID().toString().replace("-","").substring(0,20));
            transaction.setTransactionDate(LocalDate.now());
            transaction.setDescription("Money transferred Sucessfully");
            transaction.setStatus("Successful");
            transaction.setAmount(transferRequest.getAmount());
            transaction.setFromAccount(fromAccount.getAccountId());  // Set fromAccount ID

            transaction.setToAccount(toAccount.getAccountId());
            transactionRepository.save(transaction);

            return transaction;

        } catch (Exception e) {
            logTransactionHistory(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), 
                                   transferRequest.getAmount(), "Failed");
            throw new TransactionException("Transaction failed");
        }
    }
    
    
    private boolean validateTransactionPin(Account account, String transactionPin) {
        // Validate transaction PIN logic
        if (account != null) {
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            if (encoder.matches(transactionPin,account.getTransactionPin())) {
                return true;
            }
        }
        return false; // Example logic, replace with your actual validation
    }

    private void logTransactionHistory(String fromAccountId, String toAccountId, double amount, String status) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setDescription("Money transfer");
        transaction.setStatus(status);
        transaction.setAmount(amount);
        transaction.setFromAccount(fromAccountId);
        transaction.setToAccount(toAccountId);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        // Implement logic to fetch transaction history by accountId
        return transactionRepository.findAllByFromAccountOrToAccount(accountId, accountId);
    }
}
