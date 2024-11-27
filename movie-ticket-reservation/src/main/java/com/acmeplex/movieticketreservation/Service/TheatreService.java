package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Model.Theatre;
import com.acmeplex.movieticketreservation.Repository.MovieRepository;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import com.acmeplex.movieticketreservation.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public List<Map<String, Object>> getShowtimesByTheatreID(int theaterID) {
        Optional<Theatre> theatreOptional = theatreRepository.findById(theaterID);
        if (theatreOptional.isEmpty()) {
            throw new IllegalArgumentException("Theater with ID " + theaterID + " does not exist.");
        }
        Theatre theatre = theatreOptional.get();
        List<Map<String, Object>> response = new ArrayList<>();
        List<Showtime> showtimes = showtimeRepository.findByTheatreID(theaterID);
        for (Showtime showtime : showtimes) {
            Map<String, Object> showtimeData = new HashMap<>();
            showtimeData.put("showtimeID", showtime.getShowtimeID());
            showtimeData.put("time", showtime.getTime());
            showtimeData.put("date", showtime.getDate());
            showtimeData.put("movieTitle", movieRepository.findById(showtime.getMovieID()).get().getMovieTitle());
            response.add(showtimeData);
        }
        return response;
    }


}
