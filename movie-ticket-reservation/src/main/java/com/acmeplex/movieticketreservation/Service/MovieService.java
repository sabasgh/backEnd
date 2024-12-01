package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Movie;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.MovieRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Map<String, Object> getMovieDetail(int movieID) {
        // Fetch movie by ID
        Optional<Movie> movieOptional = movieRepository.findById(movieID);
        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie not found for ID: " + movieID);
        }

        Movie movie = movieOptional.get();

        // Fetch all showtimes for the movie
        List<Map<String, Object>> showtimesList = showtimeRepository.findByMovieID(movieID)
                .stream()
                .map(showtime -> {
                    // Prepare a showtime map
                    Map<String, Object> showtimeMap = new HashMap<>();
                    showtimeMap.put("showtimeID", showtime.getShowtimeID());
                    showtimeMap.put("time", showtime.getTime());
                    showtimeMap.put("date", showtime.getDate());
                    showtimeMap.put("theatreID", showtime.getTheatreID());

                    // Process seats
                    List<Map<String, Object>> seatsList = showtime.getSeats()
                            .stream()
                            .map(seat -> {
                                Map<String, Object> seatMap = new HashMap<>();
                                seatMap.put("seatID", seat.getSeatID());
                                seatMap.put("seatNumber", seat.getSeatNumber());
                                seatMap.put("status", seat.getStatus());
                                return seatMap;
                            })
                            .toList();

                    // Add seats to showtime
                    showtimeMap.put("seats", seatsList);

                    return showtimeMap;
                })
                .toList();

        // Build the final response
        Map<String, Object> response = new HashMap<>();
        response.put("movieID", movie.getMovieID());
        response.put("movieTitle", movie.getMovieTitle());
        response.put("genre", movie.getMovieGenre());
        response.put("rating", movie.getRate());
        response.put("duration", movie.getDuration());
        response.put("showtimes", showtimesList);

        return response;
    }

}
