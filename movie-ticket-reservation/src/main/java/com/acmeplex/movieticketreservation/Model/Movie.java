package com.acmeplex.movieticketreservation.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieID;
    private String movieTitle;
    private String movieGenre;
    private int rate;
    private String duration;
    private String url;

    public Movie(String movieTitle, String movieGenre, int rate, String duration, String url) {
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.rate = rate;
        this.duration = duration;
        this.url = url;
    }

    public Movie() {
    }

    public void getTheaters(){
        System.out.println("List of theatres for this movie:....");
    }
}
