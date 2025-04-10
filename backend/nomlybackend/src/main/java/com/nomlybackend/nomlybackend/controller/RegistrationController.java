package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.emails.RegistrationRequest;
import com.nomlybackend.nomlybackend.model.emails.OtpVerificationRequest;
import com.nomlybackend.nomlybackend.model.emails.RegistrationResponse;
import com.nomlybackend.nomlybackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/email")
public class RegistrationController {

    @Autowired
    private EmailService emailService;

    // For demonstration, use in-memory cache (keyed by email) to store OTP.
    private static ConcurrentHashMap<String, String> otpCache = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        // Validate registration details as needed.
        String email = registrationRequest.getEmail();

        // Generate an OTP (e.g., a 4-digit code).
        String otp = generateOtp();

        // Store the OTP (in production, store with an expiry in DB or cache)
        otpCache.put(email, otp);

        // Send OTP email using Mailgun settings via EmailService.
        emailService.sendOtpEmail(email, otp);

        // Return a success message without exposing OTP details.
        return ResponseEntity.ok(new RegistrationResponse("OTP sent to " + email));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationRequest otpRequest) {
        String email = otpRequest.getEmail();
        String otpFromRequest = otpRequest.getOtp();
        String storedOtp = otpCache.get(email);

        if (storedOtp == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        if (!storedOtp.equals(otpFromRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        // OTP verified; remove it from the cache.
        otpCache.remove(email);
        return ResponseEntity.ok(true);
    }

    private String generateOtp() {
        Random random = new Random();
        int otpInt = 1000 + random.nextInt(9000); // 4-digit OTP
        return String.valueOf(otpInt);
    }
}
