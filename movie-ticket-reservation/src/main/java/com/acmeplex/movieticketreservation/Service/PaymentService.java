package com.acmeplex.movieticketreservation.Service;
import com.acmeplex.movieticketreservation.Model.Payment;
import com.acmeplex.movieticketreservation.Repository.PaymentRepository;
import com.acmeplex.movieticketreservation.Repository.TicketRepository;
import com.acmeplex.movieticketreservation.patterns.PaymentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentContext paymentContext;

    @Autowired
    private TicketRepository ticketRepository;


    public boolean processPayment(Payment payment) {
        // Simulate payment validation logic
        if (validatePayment(payment)) {
            paymentRepository.save(payment); // Save the payment record
            return true;
        }
        return false;
    }

    private boolean validatePayment(Payment payment) {
        return payment.getCardNumber() > 0 &&
                payment.getCcv() > 0 &&
                payment.getAmount() > 0;
    }
}
