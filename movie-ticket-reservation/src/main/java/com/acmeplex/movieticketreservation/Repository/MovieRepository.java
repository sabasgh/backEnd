package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
