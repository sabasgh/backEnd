package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cardOwner;
    private long cardNum;
    private int ccv;
    private String expiry;
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RegisteredUser registeredUser;

    public PaymentInfo() {}

    public PaymentInfo(String cardOwner, long cardNum, int ccv, String expiry, String paymentType, RegisteredUser registeredUser) {
        this.cardOwner = cardOwner;
        this.cardNum = cardNum;
        this.ccv = ccv;
        this.expiry = expiry;
        this.paymentType = paymentType;
        this.registeredUser = registeredUser;
    }
}
