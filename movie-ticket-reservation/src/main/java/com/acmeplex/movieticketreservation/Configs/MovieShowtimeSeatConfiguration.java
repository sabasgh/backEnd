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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MovieShowtimeSeatConfiguration {

    @Bean
    @Order(1)
    CommandLineRunner loadData(MovieRepository movieRepository, ShowtimeRepository showtimeRepository) {
        return args -> {
            // Add all movies with release dates
            Movie movie1 = new Movie("Venom: The Last Dance", "Science Fiction, Action, Adventure", 6, "1h 49m", "https://image.tmdb.org/t/p/w1280/aosm8NMQ3UyoBVpSxyimorCQykC.jpg", LocalDate.of(2024, 11, 1));
            Movie movie2 = new Movie("Smile 2", "Horror, Mystery", 6, "2h 7m", "https://www.themoviedb.org/t/p/w1280/ht8Uv9QPv9y7K0RvUyJIaXOZTfd.jpg", LocalDate.of(2024, 10, 15));
            Movie movie3 = new Movie("The Wild Robot", "Animation, Science Fiction, Family", 8, "1h 42m", "https://image.tmdb.org/t/p/w1280/wTnV3PCVW5O92JMrFvvrRcV39RU.jpg", LocalDate.of(2024, 10, 25));
            Movie movie4 = new Movie("Gladiator II", "Action, Adventure", 7, "2h 28m", "https://image.tmdb.org/t/p/w1280/2cxhvwyEwRlysAmRH4iodkvo0z5.jpg", LocalDate.of(2024, 12, 1));
            Movie movie5 = new Movie("Wicked", "Drama, Fantasy, Romance", 8, "2h 41m", "https://image.tmdb.org/t/p/w1280/c5Tqxeo1UpBvnAc3csUm7j3hlQl.jpg", LocalDate.of(2024, 11, 20));
            Movie movie6 = new Movie("Deadpool & Wolverine", "Action, Comedy, Science Fiction", 8, "2h 8m", "https://image.tmdb.org/t/p/w1280/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg", LocalDate.of(2024, 12, 15));
            Movie movie7 = new Movie("The Godfather", "Drama, Crime", 9, "2h 55m", "https://image.tmdb.org/t/p/w1280/oaIPuKuJM8IdpgWSmNe9bBiyvRY.jpg", LocalDate.of(1972, 3, 24));
            Movie movie8 = new Movie("Parasite", "Comedy, Thriller, Drama", 9, "2h 13m", "https://image.tmdb.org/t/p/w1280/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg", LocalDate.of(2019, 5, 30));
            Movie movie9 = new Movie("Forrest Gump", "Comedy, Drama, Romance", 8, "2h 22m", "https://image.tmdb.org/t/p/w1280/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg", LocalDate.of(1994, 7, 6));

            // Save all movies
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);
            movieRepository.save(movie6);
            movieRepository.save(movie7);
            movieRepository.save(movie8);
            movieRepository.save(movie9);

            // Add existing showtimes for all movies
            List<Showtime> showtimes = new ArrayList<>();
            showtimes.addAll(createDailyShowtimes("2024-11-30", movie1.getMovieID(), movie2.getMovieID(), movie3.getMovieID(), movie4.getMovieID(), movie5.getMovieID(), movie6.getMovieID(), movie7.getMovieID(), movie8.getMovieID(), movie9.getMovieID()));
            showtimes.addAll(createDailyShowtimes("2024-12-01", movie1.getMovieID(), movie2.getMovieID(), movie3.getMovieID(), movie4.getMovieID(), movie5.getMovieID(), movie6.getMovieID(), movie7.getMovieID(), movie8.getMovieID(), movie9.getMovieID()));

            // Add additional showtimes for "Deadpool & Wolverine" at Cineplex Chinook for the next 14 days
            showtimes.addAll(createDailyShowtimesForSpecificMovie(movie6.getMovieID(), 14));

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
                List<Seat> seats = generateSeatsForShowtime(showtime);
                showtime.setSeats(seats);
                dailyShowtimes.add(showtime);
            }
            theatreID = 1; // Reset theatreID for the next movie
        }

        return dailyShowtimes;
    }

    private List<Showtime> createDailyShowtimesForSpecificMovie(int movieID, int days) {
        List<Showtime> showtimes = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String[] times = {"9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"};

        for (int i = 0; i < days; i++) {
            LocalDate date = today.plusDays(i);
            for (String time : times) {
                Showtime showtime = new Showtime();
                showtime.setTime(time);
                showtime.setDate(date.toString());
                showtime.setTheatreID(1); // Assume Cineplex Chinook has Theatre ID 1
                showtime.setMovieID(movieID);

                // Generate seats for this showtime
                List<Seat> seats = generateSeatsForShowtime(showtime);
                showtime.setSeats(seats);
                showtimes.add(showtime);
            }
        }

        return showtimes;
    }

    private List<Seat> generateSeatsForShowtime(Showtime showtime) {
        List<Seat> seats = new ArrayList<>();
        for (int number = 1; number <= 50; number++) {
            Seat seat = new Seat();
            seat.setSeatNumber(number);
            seat.setStatus("available");
            seat.setShowtime(showtime);
            seats.add(seat);
        }
        return seats;
    }
}
