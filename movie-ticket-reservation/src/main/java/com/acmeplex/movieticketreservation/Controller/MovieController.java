package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.Movie;
import com.acmeplex.movieticketreservation.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public ResponseEntity<?> getAllMovies() {
        try {
            List<Movie> allMovies = movieService.getAllMovies();
            return ResponseEntity.ok(allMovies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<?> getMovieDetails(@PathVariable int movieID) {
        try {
            Map<String, Object> movieDetails = movieService.getMovieDetail(movieID);
            return ResponseEntity.ok(movieDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
