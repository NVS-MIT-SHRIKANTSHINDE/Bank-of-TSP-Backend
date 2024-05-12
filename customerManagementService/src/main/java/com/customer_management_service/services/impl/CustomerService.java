package com.customer_management_service.services.impl;

import com.customer_management_service.entites.Customer;
import com.customer_management_service.exceptions.ResourceNotFoundException;
import com.customer_management_service.repositories.CustomerRepository;
import com.customer_management_service.services.ICustomerService;
import com.customer_management_service.services.OTPService;
import com.customer_management_service.utils.DataSecurityUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OTPService otpService;

    @Autowired
    DataSecurityUtil dataSecurityUtil;

    @Autowired
    private AadharValidator aadharValidator;


    @Autowired
    private RestTemplate restTemplate;

    @Value("${com.security.isSecurityEnabled}")
    private boolean securityEnabled;

    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Override
    public Customer create(Customer customer) throws Exception {
    	
    	        UUID uuid=UUID.randomUUID();
    	        String userId = uuid.toString().replace("-","").substring(0,20);
    	        customer.setCustomerId(userId);
                if(securityEnabled){
                    HashMap<String,String>sensitiveData=new HashMap<>();
                    sensitiveData.put("mobileNumber",customer.getPhone());
                    sensitiveData.put("emailId",customer.getEmail());
                    dataSecurityUtil.maskData(sensitiveData);
                    customer.setPhone(sensitiveData.get("mobileNumber"));
                    customer.setEmail(sensitiveData.get("emailId"));

                    if(StringUtils.hasText(customer.getAadarNumber())){
                        if(aadharValidator.isValidAadhaarNumber(customer.getAadarNumber())){
                            customer.setAadarNumber(DataSecurityUtil.maskAadharNumber(customer.getAadarNumber()));
                        }
                        else {
                            logger.warn("Invalid Aadhaar Number ");
                            customer.setAadarNumber(null);

                        }
                    }else {
                        throw new ApplicationContextException("Aadhaar number is not given by the user");
                    }
                }
    	        customer.setAccountCreationDate(LocalDateTime.now());
    	        customer.setAccountUpdateDate(LocalDateTime.now());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                customer.setPassword(encoder.encode(customer.getPassword()));
    	        return customerRepository.save(customer);
    	    }
    	  
    	  

	@Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer get(String id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));

        return customer;
    }

    @Override
    public Customer update(String id, Customer customer) {
        Customer customer1 = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));
        customer1.setName(customer.getName());
        customer1.setPhone(customer.getPhone());
        customer1.setEmail(customer.getEmail());
        customer1.setAddress(customer.getAddress());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var password = encoder.encode(customer.getPassword());
        customer1.setPassword(password);
        customer1.setAccountUpdateDate(LocalDateTime.now());
        return customerRepository.save(customer1);
    }

    @Override
    public void delete(String id) {


        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));

        // Deleting Accounts from ACCOUNT-SERVICE
        // http://localhost:8083/account/user/d79beee9-de29-4633-91f7-6be276e6e3c4

        restTemplate.delete("http://ACCOUNT-SERVICE/account/user/" + customer.getCustomerId());

        this.customerRepository.delete(customer);
    }




    public Customer loginWithPassword(String customerId, String password) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password,customer.getPassword())) {
                return customer;
            }
            else {
                throw new RuntimeException("Password does not match");
            }
        }else {
            throw new RuntimeException("Customer details ot found frm the records");
        }
    }
    


    public String loginWithOTP(String phone) {
        Customer customer = customerRepository.findByPhone(phone);
        if (customer != null) {
            // Generate OTP
            String otp = otpService.generateOTP(phone);
            // Send OTP via email, SMS, etc. (implement this part)
            // Example: smsService.sendSMS(phone, "Your OTP is: " + otp);
            
            return otp; // OTP sent successfully
        }
        throw new RuntimeException("Customer Details Not Found");
    }



}