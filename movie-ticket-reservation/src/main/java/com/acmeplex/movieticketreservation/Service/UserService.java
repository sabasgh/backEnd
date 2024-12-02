package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.PaymentInfo;
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

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("userID", user.getUserID());
        userProfile.put("name", user.getName());
        userProfile.put("email", user.getEmail());
        userProfile.put("address", user.getAddress());

        List<Map<String, Object>> paymentHistory = user.getPaymentHistory().stream().map(payment -> Map.<String, Object>of("paymentID", payment.getPaymentID(), "paymentType", payment.getPaymentType(), "amount", payment.getAmount(), "cardOwner", payment.getCardOwner(), "cardNumber", payment.getCardNumber(), "ccv", payment.getCcv(), "expiry", payment.getExpiry())).toList();
        userProfile.put("paymentHistory", paymentHistory);

        // Flatten Ticket History
        List<Map<String, Object>> ticketHistory = user.getTicketHistory().stream().map(ticket -> Map.<String, Object>of("ticketID", ticket.getTicketID(), "showtimeID", ticket.getShowtime().getShowtimeID(), "seatNumber", ticket.getSeatNumber(), "theatreID", ticket.getShowtime().getTheatreID(), "movieID", ticket.getShowtime().getMovieID(), "date", ticket.getDate(), "status", ticket.getStatus())).toList();

        userProfile.put("ticketsHistory", ticketHistory);
        userProfile.put("creditPoints", user.getCreditPoints());
        List<Map<String, Object>> paymentMethods = user.getPaymentMethods().stream().map(method -> {
            Map<String, Object> methodMap = new HashMap<>();
            methodMap.put("cardOwner", method.getCardOwner());
            methodMap.put("cardNum", method.getCardNum());
            methodMap.put("ccv", method.getCcv());
            methodMap.put("expiry", method.getExpiry());
            methodMap.put("paymentType", method.getPaymentType());
            return methodMap;
        }).collect(Collectors.toList());

        userProfile.put("paymentMethods", paymentMethods);

        List<Map<String, Object>> notifications = user.getNotifications().stream().map(notification -> {
            Map<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("message", notification.getMessage());
            return notificationMap;
        }).collect(Collectors.toList());

        userProfile.put("notificationHistory", notifications);
        return userProfile;
    }


    public RegisteredUser updateUserProfile(int userID, Map<String, Object> userDetails) {
        RegisteredUser user = registeredUserRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found for ID: " + userID));

        user.setName((String) userDetails.get("name"));
        user.setEmail((String) userDetails.get("email"));
        user.setAddress((String) userDetails.get("address"));
        user.setCreditPoints((Integer) userDetails.get("creditPoints"));

        List<Map<String, Object>> paymentMethods = (List<Map<String, Object>>) userDetails.get("paymentMethods");
        if (paymentMethods != null) {
            List<PaymentInfo> newPaymentMethods = paymentMethods.stream().map(method -> {
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setCardOwner((String) method.get("cardOwner"));
                paymentInfo.setCardNum(Long.parseLong(method.get("cardNum").toString()));
                paymentInfo.setCcv((Integer) method.get("ccv"));
                paymentInfo.setExpiry((String) method.get("expiry"));
                paymentInfo.setPaymentType((String) method.get("paymentType"));
                paymentInfo.setRegisteredUser(user);
                return paymentInfo;
            }).toList();
            user.getPaymentMethods().clear(); // Clear existing payment methods
            user.getPaymentMethods().addAll(newPaymentMethods);
        }
        return userRepository.save(user);
    }

    public User findOrCreateUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            // Create a new guest user if not found
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUserType("Guest");
            return userRepository.save(newUser);
        });
    }

    public User findUserById(int userID) {
        return userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
    }
}
