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
    private RegisteredUserRepository registeredUserRepository;

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
        Seat seat = seatService.findSeatByShowtime(showtime, seatNumber);
        if (seat == null) {
            throw new RuntimeException("Seat not found for seat number: " + seatNumber);
        }
        if ("reserved".equalsIgnoreCase(seat.getStatus())) {
            throw new RuntimeException("Seat number " + seatNumber + " is already reserved!");
        }

        seat.setStatus("reserved");
        seatRepository.save(seat);

        Ticket ticket = new Ticket(seatNumber, showtime, date, ticketStatus, user, payment);
        ticketRepository.save(ticket);
        user.getTicketHistory().add(ticket);
        if (user.getUserType().equals("Registered")) {
            RegisteredUser registeredUser = registeredUserRepository.findById(user.getUserID()).get();
            registeredUser.setCreditPoints(registeredUser.getCreditPoints() + 10);
            registeredUserRepository.save(registeredUser);
        } else {
            userRepository.save(user);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("ticketID", ticket.getTicketID());
        response.put("status", "success");
        response.put("showtimeID", ticket.getShowtime().getShowtimeID());
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

    public Map<String, Object> getTicketDetails(int ticketID) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketID);
        if (ticketOptional.isEmpty()) {
            throw new RuntimeException("Ticket with ID: " + ticketID + " does not exist!");
        }
        Ticket ticket = ticketOptional.get();
        Map<String, Object> ticketMap = new HashMap<>();
        ticketMap.put("ticketID", ticket.getTicketID());
        ticketMap.put("date", ticket.getDate());
        ticketMap.put("status", ticket.getStatus());
        ticketMap.put("seatNumber", ticket.getSeatNumber());
        ticketMap.put("showtimeID", ticket.getShowtime().getShowtimeID());
        ticketMap.put("theatreID", ticket.getShowtime().getTheatreID());
        ticketMap.put("movieID", ticket.getShowtime().getMovieID());
        ticketMap.put("userID", ticket.getUser().getUserID());
        ticketMap.put("paymentID", ticket.getPayment().getPaymentID());
        return ticketMap;
    }
}