package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatID;
    private int seatNumber;
    private String status;

    @ManyToOne
    @JoinColumn(name = "showtimeID")
    private Showtime showtime;

    public Seat(int seatNumber,String status, Showtime showtime) {
        this.seatNumber = seatNumber;
        this.status = status;
        this.showtime = showtime;
    }

    public Seat() {

    }

    public void updateSeat(){
        System.out.println("seat is updated!!");
    }
}
