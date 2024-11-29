package com.acmeplex.movieticketreservation.patterns;

import org.springframework.stereotype.Component;

@Component
public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(String paymentType) {
        switch (paymentType.toLowerCase()) {
            case "creditcard":
                this.paymentStrategy = new CreditCardPayment();
                break;
            case "debitcard":
                this.paymentStrategy = new DebitCardPayment();
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }

    public String executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set!");
        }
        paymentStrategy.pay(amount);
        return "success";
    }
}
