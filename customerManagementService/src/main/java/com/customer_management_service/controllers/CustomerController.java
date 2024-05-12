package com.customer_management_service.controllers;


import com.customer_management_service.entites.Customer;
import com.customer_management_service.payloads.ApiResponse;
import com.customer_management_service.services.ICustomerService;
import com.customer_management_service.services.OTPService;
import com.customer_management_service.services.impl.AadharValidator;
import com.customer_management_service.utils.DataSecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "", allowedHeaders = "")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private AadharValidator aadharValidator;

    @Autowired
    DataSecurityUtil dataSecurityUtil;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private OTPService otpService;

   //  Create
    @PostMapping
    public ResponseEntity<Customer> createUser(@RequestBody Customer customer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
    }
    
 //   It indicates that the method will return a ResponseEntity containing a Customer object.
 //   ResponseEntity is a class in Spring used to represent an HTTP response, allowing you to control
    //  status code, headers, and body of the response.


    // Get all

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customerService.getAll());
    }


    // Get one
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId){

        return ResponseEntity.status(HttpStatus.OK).body(customerService.get(customerId));

    }

    //delete
    @DeleteMapping("/{customerId}")
    public ApiResponse deleteCustomer(@PathVariable String customerId)
    {

        this.customerService.delete(customerId);
        return new ApiResponse(" Customer is Successfully Deleted !!", true);
    }

    //update

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String customerId)
    {

        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(customerId, customer));

    }


    @PostMapping("/login/password")
    public ResponseEntity<Customer> loginWithPassword(@RequestBody Customer customer) {
        Customer loggedInCustomer = customerService.loginWithPassword(customer.getCustomerId(), customer.getPassword());
        loggedInCustomer.setPassword(null); // Or you can set it to an empty string or any default value
        return ResponseEntity.ok(loggedInCustomer);
    }
    

    @PostMapping("/login/otp")
    public String loginWithOTP(@RequestBody Customer customer) {
        return customerService.loginWithOTP(customer.getPhone());

        }

    @PostMapping("/login/verify/{phoneNo}/{otp}")
    public boolean verifyOtp(@PathVariable String phoneNo, @PathVariable String otp){
        return otpService.verifyOTP(phoneNo, otp);
    }


}