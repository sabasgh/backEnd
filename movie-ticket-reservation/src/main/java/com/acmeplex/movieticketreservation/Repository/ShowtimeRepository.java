package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
    List<Showtime> findByMovieID(int movieID);
    List<Showtime> findByTheatreID(int theatreID);
}
