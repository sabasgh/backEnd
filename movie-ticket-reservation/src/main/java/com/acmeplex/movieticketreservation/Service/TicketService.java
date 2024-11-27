package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.*;
import com.acmeplex.movieticketreservation.Repository.RegisteredUserRepository;
import com.acmeplex.movieticketreservation.Repository.SeatRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import com.acmeplex.movieticketreservation.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public ResponseEntity<?> createTicket(int showtimeID, int seatNumber, int theatreID, int userID, String date, String ticketStatus) {
        try {
            Optional<RegisteredUser> userOptional = registeredUserRepository.findById(userID);
            Optional<Showtime> showtimeOptional = showtimeRepository.findById(showtimeID);
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("Registered user with ID " + userID + " does not exist.");
            }
            if (showtimeOptional.isEmpty()) {
                throw new IllegalArgumentException("Showtime with ID " + showtimeID + " does not exist.");
            }
            RegisteredUser user = userOptional.get();
            Ticket ticket = new Ticket(seatNumber, showtimeID, date, ticketStatus, user);
            Ticket savedTicket = ticketRepository.save(ticket);
            user.getTicketHistory().add(savedTicket);
            registeredUserRepository.save(user);
            return ResponseEntity.ok(Map.of(
                    "ticketID", savedTicket.getTicketID(),
                    "status", "success"
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage(),
                    "status", "failed"
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "An unexpected error occurred: " + ex.getMessage(),
                    "status", "failed"
            ));
        }
    }
}