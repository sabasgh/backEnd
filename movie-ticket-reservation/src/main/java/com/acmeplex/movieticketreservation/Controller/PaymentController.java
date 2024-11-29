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
    public Map<String, Object> makePayment(@RequestBody Map<String, Object> paymentRequest) {
        int ticketID = (int) paymentRequest.get("ticketID");
        String paymentType = (String) paymentRequest.get("paymentType");
        double amount = (double) paymentRequest.get("amount");

        try {
            Payment payment = paymentService.processPayment(ticketID, paymentType, amount);
            Map<String, Object> response = new HashMap<>();
            response.put("paymentID", payment.getPaymentID());
            response.put("status", "success");
            return response;
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("paymentID", null);
            response.put("status", "failed");
            response.put("error", e.getMessage());
            return response;
        }
    }
}

