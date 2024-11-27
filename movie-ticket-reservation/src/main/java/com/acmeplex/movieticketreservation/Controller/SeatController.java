package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Service.SeatService;
import com.acmeplex.movieticketreservation.Service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private ShowtimeService showtimeService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestBody Map<String, Object> payload) {
        try {
            String userType = (String) payload.get("userType");
            List<Integer> seatNumbers = (List<Integer>) payload.get("seatNumbers");
            Integer showtimeID = (Integer) payload.get("showtimeID");

            return seatService.reserveSeats(userType, seatNumbers, showtimeID);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error."));
        }
    }
}