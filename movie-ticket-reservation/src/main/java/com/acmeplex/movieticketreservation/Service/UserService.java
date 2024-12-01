package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.RegisteredUser;
import com.acmeplex.movieticketreservation.Model.User;
import com.acmeplex.movieticketreservation.Repository.RegisteredUserRepository;
import com.acmeplex.movieticketreservation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private UserRepository userRepository;

    public void registerUser(RegisteredUser user) {
        //checking if email already exists
        if (registeredUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }
        user.setUserType("Registered");
        registeredUserRepository.save(user);
    }

    public Map<String, Object> login(String email, String password) {
        Optional<RegisteredUser> userOptional = registeredUserRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            RegisteredUser user = userOptional.get();
            if (password.equals(user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("userID", user.getUserID());
                return response;
            }
        }
        return null;
    }

    public Map<String, Object> getUserProfile(int userID) {
        RegisteredUser user = registeredUserRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found for ID: " + userID));

        List<Map<String, Object>> ticketHistoryList = user.getTicketHistory().stream().map(ticket -> {
            Map<String, Object> ticketMap = new HashMap<>();
            ticketMap.put("ticketID", ticket.getTicketID());
            ticketMap.put("showtimeID", ticket.getShowtime().getShowtimeID());
            ticketMap.put("seatNumber", ticket.getSeatNumber());
            ticketMap.put("theatreID", ticket.getShowtime().getTheatreID());
            ticketMap.put("date", ticket.getDate());
            ticketMap.put("status", ticket.getStatus());
            return ticketMap;
        }).collect(Collectors.toList());

        // Build payment history
        List<Map<String, Object>> paymentHistoryList = user.getPaymentHistory().stream().map(payment -> {
            Map<String, Object> paymentMap = new HashMap<>();
            paymentMap.put("paymentID", payment.getPaymentID());
            paymentMap.put("amount", payment.getAmount());
            paymentMap.put("paymentType", payment.getPaymentType());
            paymentMap.put("cardOwner", payment.getCardOwner());
            paymentMap.put("cardNumber", payment.getCardNumber());
            paymentMap.put("expiry", payment.getExpiry());
            return paymentMap;
        }).collect(Collectors.toList());

        // Build the final response
        Map<String, Object> response = new HashMap<>();
        response.put("userID", user.getUserID());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("address", user.getAddress());
        response.put("ticketHistory", ticketHistoryList);
        response.put("paymentHistory", paymentHistoryList);

        return response;
    }

    public RegisteredUser updateUserProfile(int userID, RegisteredUser updatedUser) {
        Optional<RegisteredUser> optionalUser = registeredUserRepository.findById(userID);
        if (optionalUser.isPresent()) {
            RegisteredUser existingUser = optionalUser.get();
            // Update fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setMembershipFee(updatedUser.getMembershipFee());
            existingUser.setTicketHistory(updatedUser.getTicketHistory());
            // Save updated user
            return registeredUserRepository.save(existingUser);
        }
        return null; // User not found
    }

    public User findUserById(int userID) {
        return userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
    }
}
