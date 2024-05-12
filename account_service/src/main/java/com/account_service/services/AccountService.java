package com.account_service.services;

import com.account_service.entity.Account;

import java.util.List;

public interface AccountService {

    // Create
    Account create(Account account);

    // Get accounts
    List<Account> getAccounts();

    // Get single account
    Account getAccount(String id);

    // Get single account using customerId
    List<Account> getAccountByCustomerId(String customerId);

    // Update Account
    Account updateAccount(String id, Account account);

    // Update Balance
    Account addBalance(String accountId, int amount);
    Account withdrawBalance(String id, int amount, String customerId);

    // Delete
    void delete(String id);

    void deleteAccountUsingCustomerId(String customerId);

	Account login(String accountId, String transactionPin);

	void updateBalance(Account account);


	
}
