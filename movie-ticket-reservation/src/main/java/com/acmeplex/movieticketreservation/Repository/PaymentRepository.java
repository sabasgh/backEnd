package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
