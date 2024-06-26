package com.customer_management_service.services;

import com.customer_management_service.entites.Account;
import com.customer_management_service.entites.Customer;

import java.util.List;

public interface CustomerService {

    // create

    Customer create(Customer customer) throws Exception;


    // get all

    List<Customer> getAll();

    //get single

    Customer get(String id);


    //update

    Customer update(String id, Customer customer);

    //delete

    void delete(String id);



    public boolean loginWithPassword(String customerId, String password);
    public String loginWithOTP(String phone) ;



    }