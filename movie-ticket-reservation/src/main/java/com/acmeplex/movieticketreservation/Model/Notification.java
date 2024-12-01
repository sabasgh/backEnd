package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationID;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID", nullable = false)
    private User user;

    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public Notification() {
    }
}
