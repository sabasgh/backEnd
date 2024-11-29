package com.acmeplex.movieticketreservation.patterns;

public class DebitCardPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("Processing debit card payment of $" + amount);
    }
}
