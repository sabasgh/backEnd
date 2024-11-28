package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Seat;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimeService showtimeService;

    @Transactional
    public ResponseEntity<?> reserveSeats(String userType, List<Integer> seatNumbers, Integer showtimeID) {
        try {
            if (seatNumbers == null || seatNumbers.isEmpty()) {
                throw new IllegalArgumentException("Seat numbers cannot be null or empty.");
            }
            if (showtimeID == null) {
                throw new IllegalArgumentException("Showtime ID cannot be null.");
            }
            List<Seat> reservedSeats = new ArrayList<>();
            for (Integer seatNumber : seatNumbers) {
                // Check if the seat exists in the database
                Optional<Seat> seatOptional = seatRepository.findById(seatNumber);
                if (seatOptional.isEmpty()) {
                    throw new IllegalArgumentException("Seat number " + seatNumber + " does not exist.");
                }
                Seat seat = seatOptional.get();
                if ("reserved".equalsIgnoreCase(seat.getStatus())) {
                    throw new IllegalArgumentException("Seat number " + seatNumber + " is already reserved.");
                }

                // Reserve the seat
                seat.setStatus("reserved");
                reservedSeats.add(seat);
            }

            // Save all reserved seats at once
            seatRepository.saveAll(reservedSeats);
            return ResponseEntity.ok(Map.of("message", "Seats reserved successfully."));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while reserving seats: " + ex.getMessage(), ex);
        }
    }

    public Seat findSeatByShowtime(Showtime showtime, int seatNumber) {
        for (Seat seat : showtime.getSeats()) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }
}
