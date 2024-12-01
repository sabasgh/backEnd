package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Payment;
import com.acmeplex.movieticketreservation.Model.User;
import com.acmeplex.movieticketreservation.Repository.PaymentRepository;
import com.acmeplex.movieticketreservation.Repository.UserRepository;
import com.acmeplex.movieticketreservation.patterns.PaymentContext;
import com.acmeplex.movieticketreservation.patterns.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentContext paymentContext;


    @Transactional
    public Map<String, Object> processPayment(String email, String paymentType, double amount, String cardOwner, long cardNumber, int ccv, String expiry) {

        User user = userService.findOrCreateUserByEmail(email);
        try {
            paymentContext.setPaymentStrategy(paymentType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment type: " + paymentType);
        }
        boolean paymentSuccess = paymentContext.executePayment(amount);
        Payment payment = new Payment(paymentType, amount, cardOwner, cardNumber, ccv, expiry);
        payment.setUser(user);
        if (paymentSuccess) {
            paymentRepository.save(payment);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("paymentID", paymentSuccess ? payment.getPaymentID() : null);
        response.put("status", paymentSuccess ? "success" : "failed");
        response.put("user", user.getUserID());
        return response;
    }


}
