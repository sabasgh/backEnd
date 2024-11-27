package com.acmeplex.movieticketreservation.Configs;

import com.acmeplex.movieticketreservation.Model.Movie;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.MovieRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
public class MovieAndShowtimeConfiguration {

    @Bean
    @Order(1)
    CommandLineRunner loadData(MovieRepository movieRepository, ShowtimeRepository showtimeRepository) {
        return args -> {
            // Add sample movies
            Movie movie1 = new Movie("Movie A", "Action", 5, "2h", "https://example.com/movieA.jpg");
            Movie movie2 = new Movie("Movie B", "Comedy", 4, "1.5h", "https://example.com/movieB.jpg");
            Movie movie3 = new Movie("Movie C", "Horror", 3, "1.75h", "https://example.com/movieC.jpg");
            Movie movie4 = new Movie("Movie D", "Drama", 4, "2.25h", "https://example.com/movieD.jpg");
            Movie movie5 = new Movie("Movie E", "Sci-Fi", 5, "2.5h", "https://example.com/movieE.jpg");

            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);

            // Add sample showtimes, referencing the movie IDs
            Showtime showtime1 = new Showtime();
            showtime1.setDate("2024-11-26");
            showtime1.setTime("10:00 AM");
            showtime1.setTheatreID(1);
            showtime1.setMovieID(movie1.getMovieID());
            showtimeRepository.save(showtime1);

            Showtime showtime2 = new Showtime();
            showtime2.setDate("2024-11-26");
            showtime2.setTime("12:00 PM");
            showtime2.setTheatreID(1);
            showtime2.setMovieID(movie2.getMovieID());
            showtimeRepository.save(showtime2);

            Showtime showtime3 = new Showtime();
            showtime3.setDate("2024-11-26");
            showtime3.setTime("3:00 PM");
            showtime3.setTheatreID(2);
            showtime3.setMovieID(movie3.getMovieID());
            showtimeRepository.save(showtime3);

            Showtime showtime4 = new Showtime();
            showtime4.setDate("2024-11-26");
            showtime4.setTime("6:00 PM");
            showtime4.setTheatreID(3);
            showtime4.setMovieID(movie4.getMovieID());
            showtimeRepository.save(showtime4);

            Showtime showtime5 = new Showtime();
            showtime5.setDate("2024-11-26");
            showtime5.setTime("9:00 PM");
            showtime5.setTheatreID(4);
            showtime5.setMovieID(movie5.getMovieID());
            showtimeRepository.save(showtime5);
        };
    }
}