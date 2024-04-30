package com.TransactionService1.TransactionService1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.TransactionService1.TransactionService1.entity.Account;
import java.util.HashMap;
import java.util.Map;



@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    public Account getAccountById(String accountId) {
        return restTemplate.getForObject("http://ACCOUNT-SERVICE/account/" + accountId, Account.class);
    }

    public String updateAccount(Account account) {
        try {
            restTemplate.put("http://ACCOUNT-SERVICE/account/update", account);
            return "Account updated successfully";
        } catch (Exception e) {
            return "Error updating account: " + e.getMessage();
        }

    }
    public Account addMoney(String accountId, double d, String customerId) {
        String url = "http://ACCOUNT-SERVICE/account/addmoney/{accountID}?amount={amount}&customerId={customerId}";
        Map<String, String> params = new HashMap<>();
        params.put("accountID", accountId);
        params.put("amount", String.valueOf(d));
        params.put("customerId", customerId);

        return restTemplate.exchange(url, HttpMethod.PUT, null, Account.class, params).getBody();
    }
}

