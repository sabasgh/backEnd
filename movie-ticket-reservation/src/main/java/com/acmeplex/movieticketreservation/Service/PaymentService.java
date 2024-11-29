package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Payment;
import com.acmeplex.movieticketreservation.Repository.PaymentRepository;
import com.acmeplex.movieticketreservation.patterns.PaymentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentContext paymentContext;

    public Payment processPayment(int ticketID, String paymentType, double amount) {
        try {
            paymentContext.setPaymentStrategy(paymentType);
            String status = paymentContext.executePayment(amount);
            Payment payment = new Payment(paymentType, amount);
            Payment savedPayment = paymentRepository.save(payment);
            return savedPayment;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }
}
