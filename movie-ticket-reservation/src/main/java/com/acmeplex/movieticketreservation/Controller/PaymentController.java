package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.Payment;
import com.acmeplex.movieticketreservation.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Map<String, Object>> makePayment(@RequestBody Payment payment) {
        boolean isPaymentSuccessful = paymentService.processPayment(payment);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("paymentID", isPaymentSuccessful ? payment.getPaymentID() : null);
        response.put("status", isPaymentSuccessful ? "success" : "failed");

        return ResponseEntity.ok(response);
    }
}

