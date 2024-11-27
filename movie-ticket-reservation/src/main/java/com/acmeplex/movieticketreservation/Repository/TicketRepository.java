package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
