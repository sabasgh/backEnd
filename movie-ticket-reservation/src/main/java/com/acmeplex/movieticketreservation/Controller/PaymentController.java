package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.Payment;
import com.acmeplex.movieticketreservation.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<?> makePayment(@RequestBody Map<String, Object> paymentRequest) {
        try {
            String email = (String) paymentRequest.get("email");
            String paymentType = (String) paymentRequest.get("paymentType");
            double amount = ((Number) paymentRequest.get("amount")).doubleValue();
            String cardOwner = (String) paymentRequest.get("cardOwner");
            long cardNumber = Long.parseLong(paymentRequest.get("cardNumber").toString());
            int ccv = (int) paymentRequest.get("ccv");
            String expiry = (String) paymentRequest.get("expiry");
            Map<String, Object> response = paymentService.processPayment(email, paymentType, amount, cardOwner, cardNumber, ccv, expiry);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}

