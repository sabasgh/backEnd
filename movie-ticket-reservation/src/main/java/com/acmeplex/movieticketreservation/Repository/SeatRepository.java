package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
