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
    ResponseEntity<?> creatTicket(@RequestBody Map<String, Object> payload) {
        try {
            int showtimeID = (int) payload.get("showtimeID");
            int seatNumber = (int) payload.get("seatNumber");
            int theatreID = (int) payload.get("theatreID");
            int userID = (int) payload.get("userID");
            String date = (String) payload.get("date");
            String ticketStatus = (String) payload.get("ticketStatus");
            return ticketService.createTicket(showtimeID, seatNumber, theatreID, userID, date, ticketStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in ticket generating: " + e.getMessage());
        }
    }

    @GetMapping("/{ticketID}")
    ResponseEntity<?> cancelTicket(@PathVariable int ticketID){
        try {
            double refundAmount = ticketService.cancelTicket(ticketID);
            return ResponseEntity.ok(Map.of("refundAmount", refundAmount));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in ticket cancelation: " + e.getMessage());
        }
    }
}
