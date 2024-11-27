package com.acmeplex.movieticketreservation.Configs;

import com.acmeplex.movieticketreservation.Model.Seat;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.SeatRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShowtimeAndSeatConfiguration {
    @Bean
    @Order(3)
    CommandLineRunner loadShowtimeAndSeats(ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        return args -> {
            // Fetch all showtimes from the database
            List<Showtime> showtimes = showtimeRepository.findAll();

            // Generate seats for each showtime
            for (Showtime showtime : showtimes) {
                List<Seat> seats = new ArrayList<>();
                // Create 10 seats for each showtime
                for (int i = 1; i <= 10; i++) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(i);
                    seat.setSeatRow("A"); // You can dynamically change rows as needed
                    seat.setStatus(i % 2 == 0 ? "reserved" : "available"); // Alternate status for example
                    seat.setShowtime(showtime); // Associate seat with the current showtime
                    seats.add(seat);
                }
                seatRepository.saveAll(seats); // Save seats for the current showtime
            }
        };
    }
}