package com.acmeplex.movieticketreservation.Configs;

import com.acmeplex.movieticketreservation.Model.Movie;
import com.acmeplex.movieticketreservation.Model.Seat;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.MovieRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MovieShowtimeSeatConfiguration {

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

            // Add showtimes for each movie with multiple days
            List<Showtime> showtimes = new ArrayList<>();

            // Add showtimes for 2024-11-30
            showtimes.addAll(createDailyShowtimes("2024-11-30", movie1.getMovieID(), movie2.getMovieID(), movie3.getMovieID(), movie4.getMovieID(), movie5.getMovieID(), movie6.getMovieID()));

            // Add showtimes for 2024-12-01
            showtimes.addAll(createDailyShowtimes("2024-12-01", movie1.getMovieID(), movie2.getMovieID(), movie3.getMovieID(), movie4.getMovieID(), movie5.getMovieID(), movie6.getMovieID()));

            // Add showtimes for 2024-12-02
            showtimes.addAll(createDailyShowtimes("2024-12-02", movie1.getMovieID(), movie2.getMovieID(), movie3.getMovieID(), movie4.getMovieID(), movie5.getMovieID(), movie6.getMovieID()));

            // Save all showtimes
            showtimeRepository.saveAll(showtimes);
        };
    }

    private List<Showtime> createDailyShowtimes(String date, int... movieIDs) {
        List<Showtime> dailyShowtimes = new ArrayList<>();
        String[] times = {"10:00 AM", "1:00 PM", "3:30 PM", "6:00 PM", "8:45 PM"};
        int theatreID = 1;

        for (int movieID : movieIDs) {
            for (String time : times) {
                Showtime showtime = new Showtime();
                showtime.setTime(time);
                showtime.setDate(date);
                showtime.setTheatreID(theatreID++);
                showtime.setMovieID(movieID);

                // Generate seats for this showtime
                List<Seat> seats = new ArrayList<>();
                for (int number = 1; number <= 50; number++) { // Create 50 seats
                    Seat seat = new Seat();
                    seat.setSeatNumber(number);
                    seat.setStatus("available"); // All seats start as available
                    seat.setShowtime(showtime); // Link seat to the showtime
                    seats.add(seat);
                }

                showtime.setSeats(seats); // Assign seats to the showtime
                dailyShowtimes.add(showtime);
            }
            theatreID = 1; // Reset theatreID for the next movie
        }

        return dailyShowtimes;
    }
}