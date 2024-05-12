package com.EmailVerification.EmailAuthentification;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VerificationController {

    @Autowired
    private JavaMailSender javaMailSender;

    private Map<String, String> otpMap = new HashMap<>(); // Map to store email and OTP

    @GetMapping("/hello")
    public String hello() {
        return "Hello, welcome to our API!";
    }

    @PostMapping("sendotp")
    public String sendOTP(@RequestParam("email") String email, Model model) {
        String otp = generateOTP(); // Generate OTP
        sendOtpEmail(email, otp); // Send OTP via email
        otpMap.put(email, otp); // Store OTP with email in the map
        model.addAttribute("email", email);
        return "otpsent"; // Return a page confirming OTP has been sent
    }

    @PostMapping("verifyotp")
    public String verifyOTP(@RequestParam("otp") String enteredOTP, @RequestParam("email") String email, Model model) {
        String storedOTP = otpMap.get(email); // Retrieve OTP from the map using email
        if (storedOTP != null && storedOTP.equals(enteredOTP)) {
            // If OTP is correct, redirect to account details page
            return "ok";
        } else {
            return "invalidotp"; // Return to signup page with error message
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otpValue);
    }

    private void sendOtpEmail(String userEmail, String otp) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(userEmail);
            helper.setSubject("OTP for Signup ");
            helper.setText("Your OTP for signing up and booking in our Travel App is: " + otp 
                    + ". Please use this OTP to complete your registration and booking process.");
            javaMailSender.send(message);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            // Handle exceptions, such as MailException
        }
    }
}
