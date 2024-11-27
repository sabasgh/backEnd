package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeID;

    private String time;
    private String date;
    private int theatreID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showtime")
    private List<Seat> seats;

    private int movieID;

    public Showtime(String time, String date, int theatreID, List<Seat> seats, int movieID) {
        this.time = time;
        this.date = date;
        this.theatreID = theatreID;
        this.seats = seats;
        this.movieID = movieID;
    }

    public Showtime() {

    }
}
