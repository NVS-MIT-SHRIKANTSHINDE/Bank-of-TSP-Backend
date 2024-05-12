package com.customer_management_service.services;

import com.customer_management_service.entites.Customer;

import java.util.List;

public interface ICustomerService {

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



    public Customer loginWithPassword(String customerId, String password);
    public String loginWithOTP(String phone) ;


    }