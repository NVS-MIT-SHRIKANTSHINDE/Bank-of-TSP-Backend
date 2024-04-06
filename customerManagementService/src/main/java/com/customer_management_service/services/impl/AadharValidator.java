package com.customer_management_service.services.impl;

import com.customer_management_service.entites.Customer;
import com.customer_management_service.repositories.CustomerRepository;
import com.customer_management_service.utils.DataSecurityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class AadharValidator {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired(required=true)
    DataSecurityUtil dataSecurityUtil;
    public boolean isValidAadhaarNumber(String aadharNumber) {
        Customer customer =  customerRepository.findByAadarNumber(aadharNumber);
        if(ObjectUtils.isEmpty(customer)){
            return aadharNumber != null && aadharNumber.matches("^\\d{12}");
        }
        else {
            throw new RuntimeException("Aadhar Number is Already exist in database records");
        }



    }
}