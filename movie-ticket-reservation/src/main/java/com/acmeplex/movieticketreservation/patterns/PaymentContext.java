package com.acmeplex.movieticketreservation.patterns;

import org.springframework.stereotype.Component;

@Component
public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(String paymentType) {
        switch (paymentType.toLowerCase()) {
            case "credit":
                this.paymentStrategy = new CreditCardPayment();
                break;
            case "debit":
                this.paymentStrategy = new DebitCardPayment();
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }

    public Boolean executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set!");
        }
        paymentStrategy.pay(amount);
        return true;
    }
}
