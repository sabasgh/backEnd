package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Double amount;

    public Payment(String paymentType, Double amount) {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public Payment() {
    }

    public void makePayment(){
        System.out.println("payment is made...");
    }
    public void refund(){
        System.out.println("refund is proccessed...");
    }
}
