package com.customer_management_service.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    public String generateOTP(String username) {
        // Generate OTP
        String otp = generateRandomOTP();
        // Store OTP for user
        otpStore.put(username, otp);
        return otp;
    }

    public boolean verifyOTP(String username, String otp) {
        // Retrieve OTP for user
        String storedOTP = otpStore.get(username);
        // Verify OTP
        return storedOTP != null && storedOTP.equals(otp);
    }

    private String generateRandomOTP() {
        // Generate random OTP (implement your logic here)
        return String.format("%06d", new Random().nextInt(999999));
    }
}