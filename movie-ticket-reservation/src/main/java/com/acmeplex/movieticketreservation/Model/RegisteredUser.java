package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class RegisteredUser extends User{
    private String name;
    private Float membershipFee = 100.0f;
    private int creditPoints;
    private String address;
    private String password;

    @OneToMany(mappedBy = "registeredUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentInfo> paymentMethods;



    public RegisteredUser() {
    }
}
