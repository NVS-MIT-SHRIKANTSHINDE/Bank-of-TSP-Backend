package com.account_service.services.Impl;

import com.account_service.entity.Account;
import com.account_service.entity.Customer;
import com.account_service.exceptions.ResourceNotFoundException;
import com.account_service.repositories.AccountRepository;
import com.account_service.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class
AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    
    

    @Override
    public Account create(Account account) {
        UUID uuid = UUID.randomUUID();
        String accountId = uuid.toString().replace("-","").substring(0,20);
        account.setAccountId(accountId);

        Date currentDate = new Date();
        account.setAccountOpeningDate(currentDate);
        account.setLastActivity(currentDate);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + account.getCustomerId(), Customer.class);
        account.setCustomer(customer);

        return account;
    }

    @Override
    public List<Account> getAccountByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public Account updateAccount(String id, Account account) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account with given id not found try again with correct details!!"));
        newAccount.setAccountType(account.getAccountType());
        newAccount.setLastActivity(new Date());
        return accountRepository.save(newAccount);
    }

    @Override
    public Account addBalance(String accountId, int amount) {
        Account newAccount = accountRepository.findById(accountId).orElseThrow(() ->
                new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        Account account = restTemplate.getForObject("http://ACCOUNT-SERVICE/account/" + accountId, Account.class);

        if (account == null) {
            throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
        } else {
            double newBalance = newAccount.getBalance() + amount;
            newAccount.setBalance(newBalance);
            newAccount.setLastActivity(new Date());
            return accountRepository.save(newAccount);
        }
    }

    @Override
    public Account withdrawBalance(String id, int amount, String customerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + customerId, Customer.class);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
        } else {
            double newBalance = newAccount.getBalance() - amount;
            newAccount.setBalance(newBalance);
            newAccount.setLastActivity(new Date());
            return accountRepository.save(newAccount);
        }
    }

    @Override
    public void delete(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account with given id not found !!"));
        this.accountRepository.delete(account);
    }

    @Override
    public void deleteAccountUsingCustomerId(String customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        for (Account account : accounts) {
            this.accountRepository.delete(account);
        }
    }

    @Override
    public Account login(String accountId, String transactionPin) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + accountId + " not found"));

        // Compare the provided transactionPin with the stored encrypted pin
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(transactionPin, account.getTransactionPin())) {
            throw new RuntimeException("Invalid credentials");
        }

        return account;
    }

    @Override
    public void updateBalance(Account account) {
        List<Account> acc = getAccounts();
        var updateAccount = acc.stream()
                .filter(x -> x.getAccountId().equals(account.getAccountId()))
                .findFirst();

        if (updateAccount.isPresent()) {
            Account foundAccount = updateAccount.get();
            foundAccount.setBalance(account.getBalance());

            // Save the updated account
            accountRepository.save(foundAccount);
        } else {
            throw new ResourceNotFoundException("Account with id " + account.getAccountId() + " not found");
        }
    }

}
