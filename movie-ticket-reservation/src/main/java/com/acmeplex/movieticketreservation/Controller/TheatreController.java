package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.Theatre;
import com.acmeplex.movieticketreservation.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTheatres() {
        try {
            List<Theatre> allTheatres = theatreService.getAllTheatres();
            return ResponseEntity.ok(allTheatres);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{theaterID}/showtimes")
    public ResponseEntity<?> getTheatreShowtimes(@PathVariable int theaterID) {
        try {
            List<Map<String, Object>> list = theatreService.getShowtimesByTheatreID(theaterID);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
