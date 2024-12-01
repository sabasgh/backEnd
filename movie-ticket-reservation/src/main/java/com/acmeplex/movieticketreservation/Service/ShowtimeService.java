package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Seat;
import com.acmeplex.movieticketreservation.Model.Showtime;
import com.acmeplex.movieticketreservation.Repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Map<String, Object>> getSeatsForShowtime(int showtimeID) {
        Optional<Showtime> showtimeOptional = showtimeRepository.findById(showtimeID);
        if (showtimeOptional.isEmpty()) {
            throw new RuntimeException("Showtime not found for ID: " + showtimeID);
        }
        Showtime showtime = showtimeOptional.get();
        List<Map<String, Object>> seatsList = new ArrayList<>();
        for (Seat seat : showtime.getSeats()) {
            Map<String, Object> map = new HashMap<>();
            map.put("seatNumber", seat.getSeatNumber());
            map.put("status", seat.getStatus());
            seatsList.add(map);
        }
        return seatsList;
    }
}
