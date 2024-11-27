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
            // Add updated movies
            Movie movie1 = new Movie("Venom: The Last Dance", "Science Fiction, Action, Adventure", 6, "1h 49m", "https://image.tmdb.org/t/p/w1280/aosm8NMQ3UyoBVpSxyimorCQykC.jpg");
            Movie movie2 = new Movie("Smile 2", "Horror, Mystery", 6, "2h 7m", "https://www.themoviedb.org/t/p/w1280/ht8Uv9QPv9y7K0RvUyJIaXOZTfd.jpg");
            Movie movie3 = new Movie("The Wild Robot", "Animation, Science Fiction, Family", 8, "1h 42m", "https://image.tmdb.org/t/p/w1280/wTnV3PCVW5O92JMrFvvrRcV39RU.jpg");
            Movie movie4 = new Movie("Gladiator II", "Action, Adventure", 7, "2h 28m", "https://image.tmdb.org/t/p/w1280/2cxhvwyEwRlysAmRH4iodkvo0z5.jpg");
            Movie movie5 = new Movie("Wicked", "Drama, Fantasy, Romance", 8, "2h 41m", "https://image.tmdb.org/t/p/w1280/c5Tqxeo1UpBvnAc3csUm7j3hlQl.jpg");
            Movie movie6 = new Movie("Deadpool & Wolverine", "Action, Comedy, Science Fiction", 8, "2h 8m", "https://image.tmdb.org/t/p/w1280/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg");

            // Save movies first so their IDs are available
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);
            movieRepository.save(movie6);

            // Add showtimes for each movie
            Showtime showtime1 = new Showtime();
            showtime1.setDate("2024-11-30");
            showtime1.setTime("10:00 AM");
            showtime1.setTheatreID(1);
            showtime1.setMovieID(movie1.getMovieID());
            showtimeRepository.save(showtime1);

            Showtime showtime2 = new Showtime();
            showtime2.setDate("2024-11-30");
            showtime2.setTime("1:00 PM");
            showtime2.setTheatreID(2);
            showtime2.setMovieID(movie2.getMovieID());
            showtimeRepository.save(showtime2);

            Showtime showtime3 = new Showtime();
            showtime3.setDate("2024-11-30");
            showtime3.setTime("3:00 PM");
            showtime3.setTheatreID(3);
            showtime3.setMovieID(movie3.getMovieID());
            showtimeRepository.save(showtime3);

            Showtime showtime4 = new Showtime();
            showtime4.setDate("2024-11-30");
            showtime4.setTime("5:00 PM");
            showtime4.setTheatreID(4);
            showtime4.setMovieID(movie4.getMovieID());
            showtimeRepository.save(showtime4);

            Showtime showtime5 = new Showtime();
            showtime5.setDate("2024-11-30");
            showtime5.setTime("7:00 PM");
            showtime5.setTheatreID(5);
            showtime5.setMovieID(movie5.getMovieID());
            showtimeRepository.save(showtime5);

            Showtime showtime6 = new Showtime();
            showtime6.setDate("2024-11-30");
            showtime6.setTime("9:00 PM");
            showtime6.setTheatreID(1);
            showtime6.setMovieID(movie6.getMovieID());
            showtimeRepository.save(showtime6);
        };
    }
}
