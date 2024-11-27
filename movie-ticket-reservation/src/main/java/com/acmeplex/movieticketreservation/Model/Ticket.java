package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;

    private int seatNumber;
    private int showtimeID;
    private String date;
    private String status;


    @ManyToOne
    @JoinColumn(name = "registered_user_id", referencedColumnName = "userID", nullable = false)
    private RegisteredUser registeredUser;

    public Ticket(int seatNumber, int showtimeID, String date, String status, RegisteredUser registeredUser) {
        this.seatNumber = seatNumber;
        this.showtimeID = showtimeID;
        this.date = date;
        this.status = status;
        this.registeredUser = registeredUser;
    }

    public Ticket() {
    }

    public void cancelTicket(){
        System.out.println("ticket is cancelled!");
    }
}
