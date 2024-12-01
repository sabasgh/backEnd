package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.*;
import com.acmeplex.movieticketreservation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Autowired
    private PaymentRepository paymentRepository;


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

    @Transactional
    public Map<String, Object> createTicket(int showtimeID, int seatNumber, int theatreID, int userID, int paymentID, String date, String ticketStatus) {
        if (isPaid(paymentID)) {
            throw new RuntimeException("This ticket is already paid!");
        }
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userID));

        Payment payment = paymentRepository.findById(paymentID)
                .orElseThrow(() -> new RuntimeException("Payment not found for ID: " + paymentID));

        Showtime showtime = showtimeRepository.findById(showtimeID)
                .orElseThrow(() -> new RuntimeException("showtime not found for ID: " + showtimeID));

        Ticket ticket = new Ticket(seatNumber, showtime, date, ticketStatus, user, payment);
        ticketRepository.save(ticket);
        user.getTicketHistory().add(ticket);
        userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("ticketID", ticket.getTicketID());
        response.put("status", "success");
        response.put("showtimeID",ticket.getShowtime().getShowtimeID());
        return response;
    }

    private boolean isPaid(int paymentID) {
        Optional<Ticket> ticketOptional = ticketRepository.findByPayment_PaymentID(paymentID);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            if (ticket.getStatus().equals("booked")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}