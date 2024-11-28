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

    @ManyToOne
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtimeID", nullable = false)
    private Showtime showtime;
    private String date;
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID", nullable = false)
    private User user;

    public static final double TICKET_PRICE = 20.0; // Hardcoded ticket price

    public Ticket(int seatNumber, Showtime showtime, String date, String status, User user) {
        this.seatNumber = seatNumber;
        this.showtime = showtime;
        this.date = date;
        this.status = status;
        this.user = user;
    }

    public Ticket() {
    }

    public void cancelTicket(){
        System.out.println("ticket is cancelled!");
    }
}
