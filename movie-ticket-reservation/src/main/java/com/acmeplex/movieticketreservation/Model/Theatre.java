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
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int theatreID;

    private String theatreName;
    private String theatreLocation;
    private int capacity;

    public Theatre(String theatreName, String theatreLocation, int capacity) {
        this.theatreName = theatreName;
        this.theatreLocation = theatreLocation;
        this.capacity = capacity;
    }

    public Theatre() {
    }

    public void getShowtimes(){
        System.out.println("showtimes generated!!");
    }
}
