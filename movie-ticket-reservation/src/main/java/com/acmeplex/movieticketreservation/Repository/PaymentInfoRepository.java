package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Integer> {
}
