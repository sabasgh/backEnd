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
            Movie movie7 = new Movie("Alien Romulus", "Horror, Science Fiction", 7, "1h 59m", "https://image.tmdb.org/t/p/w1280/2uSWRTtCG336nuBiG8jOTEUKSy8.jpg", LocalDate.of(2024, 11, 10));
            Movie movie8 = new Movie("Inside Out 2", "Animation", 8, "1h 37m", "https://image.tmdb.org/t/p/w1280/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg", LocalDate.of(2024, 11, 18));
            Movie movie9 = new Movie("Bob the Builder: Bob's Big Plan", "Animation", 1, "1h 17m", "https://image.tmdb.org/t/p/w1280/xQhLDWAGbWHu1YwrSdOPLkxoMXv.jpg", LocalDate.of(2024, 10, 5));
            Movie movie10 = new Movie("Joker: Folie à Deux", "Drama, Crime, Thriller", 5, "2h 18m", "https://image.tmdb.org/t/p/w1280/if8QiqCI7WAGImKcJCfzp6VTyKA.jpg", LocalDate.of(2024, 11, 25));
            Movie movie11 = new Movie("Spider-Man: Across the Spider-Verse", "Animation, Action, Adventure, Science Fiction", 10, "2h 20m", "https://image.tmdb.org/t/p/w1280/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg", LocalDate.of(2024, 11, 5));
            Movie movie12 = new Movie("Despicable Me 4", "Animation, Family, Comedy, Action", 7, "1h 34m", "https://image.tmdb.org/t/p/w1280/wWba3TaojhK7NdycRhoQpsG0FaH.jpg", LocalDate.of(2024, 11, 12));
            Movie movie13 = new Movie("Barbie", "Comedy, Adventure", 7, "1h 54m", "https://image.tmdb.org/t/p/w1280/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg", LocalDate.of(2024, 10, 20));
            Movie movie14 = new Movie("Inception", "Action, Science Fiction, Adventure", 8, "2h 28m", "https://image.tmdb.org/t/p/w1280/ljsZTbVsrQSqZgWeep2B1QiDKuh.jpg", LocalDate.of(2010, 7, 16));
            Movie movie15 = new Movie("Interstellar", "Adventure, Drama, Science Fiction", 8, "2h 49m", "https://image.tmdb.org/t/p/w1280/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", LocalDate.of(2014, 11, 7));

            // Save all movies
            movieRepository.saveAll(List.of(
                    movie1, movie2, movie3, movie4, movie5,
                    movie6, movie7, movie8, movie9, movie10,
                    movie11, movie12, movie13, movie14, movie15
            ));

            // Generate showtimes for the next few weeks
            List<Showtime> showtimes = new ArrayList<>();

            // List of all movies
            List<Movie> allMovies = List.of(
                    movie1, movie2, movie3, movie4, movie5,
                    movie6, movie7, movie8, movie9, movie10,
                    movie11, movie12, movie13, movie14, movie15
            );

            // Generate showtimes for each movie
            for (Movie movie : allMovies) {
                showtimes.addAll(createShowtimesForMovie(movie));
            }

            // Save all showtimes
            showtimeRepository.saveAll(showtimes);
        };
    }

    private List<Showtime> createShowtimesForMovie(Movie movie) {
        List<Showtime> showtimes = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusWeeks(4); // Generate showtimes for the next 4 weeks
        String[] times = {"10:00 AM", "3:00 PM", "10:00 PM"};
        int[] theatreIDs = {1, 2, 3}; // Assuming you have at least 3 theatres

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            // Only add showtimes if the movie has been released
            if (!date.isBefore(movie.getReleaseDate())) {
                for (int theatreID : theatreIDs) {
                    for (String time : times) {
                        Showtime showtime = new Showtime();
                        showtime.setTime(time);
                        showtime.setDate(date.toString());
                        showtime.setTheatreID(theatreID);
                        showtime.setMovieID(movie.getMovieID());

                        // Generate seats for this showtime
                        List<Seat> seats = generateSeatsForShowtime(showtime);
                        showtime.setSeats(seats);
                        showtimes.add(showtime);
                    }
                }
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