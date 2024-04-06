package com.customer_management_service.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Component
public class OTPService {
	
	 private Map<String, String> otpStore = new HashMap<>();

	    public String generateOTP(String phone) {
	        // Generate OTP
	        String otp = generateRandomOTP();
	        // Store OTP for user
	        otpStore.put(phone, otp);
	        return otp;
	    }

	    public boolean verifyOTP(String phone, String otp) {
	        // Retrieve OTP for user
	        String storedOTP = otpStore.get(phone);
	        // Verify OTP
	        return storedOTP != null && storedOTP.equals(otp);
	    }

	    private String generateRandomOTP() {
	        // Generate random OTP
	        StringBuilder otpBuilder = new StringBuilder();
	        Random random = new Random();
	        for (int i = 0; i < 6; i++) {
	            otpBuilder.append(random.nextInt(10));
	        }
	        return otpBuilder.toString();
	    }
}
