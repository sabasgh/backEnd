package com.acmeplex.movieticketreservation.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/home")
    public String home() {
        return "Welcome to the Movie Ticket Reservation API!";
    }
}
