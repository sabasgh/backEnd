package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;

    private String paymentType;
    private double amount;
    private String cardOwner;
    private long cardNumber;
    private int ccv;
    private String expiry;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID", nullable = false)
    private User user;

    public Payment(String paymentType, double amount, String cardOwner, long cardNumber, int ccv, String expiry) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.cardOwner = cardOwner;
        this.cardNumber = cardNumber;
        this.ccv = ccv;
        this.expiry = expiry;
    }

    public Payment() {
    }

    public void makePayment() {
        System.out.println("payment is made...");
    }

    public void refund() {
        System.out.println("refund is proccessed...");
    }
}
