package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findByPayment_PaymentID(int paymentID);
}
