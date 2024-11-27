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
        Optional<Movie> movieOptional = movieRepository.findById(movieID);
        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie not found for ID: " + movieID);
        }
        Movie movie = movieOptional.get();
        List<Showtime> showtimes = showtimeRepository.findByMovieID(movieID);
        Map<String, Object> response = new HashMap<>();
        response.put("movieID", movie.getMovieID());
        response.put("genre", movie.getMovieGenre());
        response.put("movieTitle", movie.getMovieTitle());
        response.put("duration", movie.getDuration());
        response.put("rating", movie.getRate());
        response.put("showtimes", showtimes);
        return response;

    }
}
