package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
}
