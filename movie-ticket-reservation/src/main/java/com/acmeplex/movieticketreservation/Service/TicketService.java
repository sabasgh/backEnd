package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.*;
import com.acmeplex.movieticketreservation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatService seatService;

    @Transactional
    public ResponseEntity<?> createTicket(int showtimeID, int seatNumber, int theatreID, int userID, String date, String ticketStatus) {
        try {
            Optional<User> userOptional = userRepository.findById(userID);
            Optional<Showtime> showtimeOptional = showtimeRepository.findById(showtimeID);
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User with ID " + userID + " does not exist.");
            }
            if (showtimeOptional.isEmpty()) {
                throw new IllegalArgumentException("Showtime with ID " + showtimeID + " does not exist.");
            }
            Optional<Seat> seatOptional = Optional.ofNullable(seatService.findSeatByShowtime(showtimeOptional.get(), seatNumber));
            if (seatOptional.isEmpty()) {
                throw new IllegalArgumentException("Seat with Number " + seatNumber + " does not exist in Showtime with ID" + showtimeID);
            }
            Seat seat = seatOptional.get();
            if (seat.getStatus().equals("Reserved")) {
                throw new IllegalArgumentException("Seat with Number " + seatNumber + "in Showtime with ID " + showtimeID + " is already reserved!");
            }
            seat.setStatus("Reserved");
            seatRepository.save(seat);
            User user = userOptional.get();
            Ticket ticket = new Ticket(seatNumber, showtimeOptional.get(), date, ticketStatus, user);
            Ticket savedTicket = ticketRepository.save(ticket);
            user.getTicketHistory().add(savedTicket);
            userRepository.save(user);
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

    @Transactional
    public double cancelTicket(int ticketID) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketID);
        if (ticketOptional.isEmpty()) {
            throw new IllegalArgumentException("Ticket with ID " + ticketID + " does not exist.");
        }
        Ticket ticket = ticketOptional.get();
        User user = ticket.getUser();
        Seat ticketSeat = seatService.findSeatByShowtime(ticket.getShowtime(), ticket.getSeatNumber());
        if (ticketSeat == null) {
            throw new IllegalArgumentException("Seat not found!");
        }
        ticketSeat.setStatus("Available");
        seatRepository.save(ticketSeat);
        ticket.setStatus("Cancelled");
        ticketRepository.save(ticket);
        return calculateRefund(user);
    }

    private double calculateRefund(User user) {
        return user.getUserType().equals("Registered") ? Ticket.TICKET_PRICE : Ticket.TICKET_PRICE * 0.85;
    }
}