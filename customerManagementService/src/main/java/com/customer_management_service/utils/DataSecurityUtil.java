package com.customer_management_service.utils;



import com.customer_management_service.entites.Customer;
import com.customer_management_service.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author RutvikJ
 */

@Component
@Slf4j
public class DataSecurityUtil {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean authenticateAadharWithMobile(String aadharNumber, String mobileNumber) {
        // Find customer by Aadhar number
        Customer customer = customerRepository.findByAadarNumber(aadharNumber);

        // If customer not found or Aadhar number doesn't match, return false
        if (customer == null || !aadharNumber.equals(customer.getAadarNumber())) {
            return false;
        }

        // Check if the provided mobile number matches the customer's mobile number
        return mobileNumber.equals(customer.getPhone());
    }

    public static String maskAadharNumber(String aadharNumber) {
        if (aadharNumber == null || aadharNumber.length() < 4) {
            return aadharNumber; // Do not mask if Aadhar number is null or less than 4 characters
        }

        int length = aadharNumber.length();
        int lastFourDigitsLength = Math.min(length, 4); // Get the length of the last four digits to keep unmasked
        int maskedLength = length - lastFourDigitsLength; // Calculate the length of masked characters

        StringBuilder maskedAadhar = new StringBuilder();
        for (int i = 0; i < maskedLength; i++) {
            maskedAadhar.append('*'); // Mask the characters before the last four digits
        }
        maskedAadhar.append(aadharNumber.substring(maskedLength)); // Append the last four digits

        return maskedAadhar.toString();
    }

    public String maskData(Map<String, String> data) {
        if (data.containsKey("mobileNumber")) {
            String mobileNumber = data.get("mobileNumber");
            if (mobileNumber.length() >= 10 && mobileNumber.length() < 12) {
                String maskedNumber = "******" + mobileNumber.substring(mobileNumber.length() - 4);
                data.put("mobileNumber", maskedNumber);
            }else {
                log.debug("invalid mobile number , input mobile is less than 10 digits");
            }
        }

        if (data.containsKey("emailId")) {
            String email = data.get("emailId");
            if (email.contains("@")) {
                String[] parts = email.split("@");
                if (parts.length == 2) {
                    data.put("emailId", "******@" + parts[1]);
                }
            }
        }

        return data.toString();
    }
}