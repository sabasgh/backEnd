package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/")
    public ResponseEntity<?> createTicket(@RequestBody Map<String, Object> ticketRequest) {
        try {
            int showtimeID = (int) ticketRequest.get("showtimeID");
            int seatNumber = (int) ticketRequest.get("seatNumber");
            int theatreID = (int) ticketRequest.get("theatreID");
            int userID = (int) ticketRequest.get("userID");
            int paymentID = (int) ticketRequest.get("paymentID");
            String date = (String) ticketRequest.get("date");
            String ticketStatus = (String) ticketRequest.get("ticketStatus");
            Map<String, Object> response = ticketService.createTicket(showtimeID, seatNumber, theatreID, userID, paymentID, date, ticketStatus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in ticket generating: " + e.getMessage());
        }
    }

    @GetMapping("/cancel/{ticketID}")
    ResponseEntity<?> cancelTicket(@PathVariable int ticketID) {
        try {
            double refundAmount = ticketService.cancelTicket(ticketID);
            return ResponseEntity.ok(Map.of("refundAmount", refundAmount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in ticket cancelation: " + e.getMessage());
        }
    }

    @GetMapping("/{ticketID}")
    ResponseEntity<?> getTicketDetails(@PathVariable int ticketID) {
        try {
            Map<String, Object> ticketInfo = ticketService.getTicketDetails(ticketID);
            return ResponseEntity.ok(ticketInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
