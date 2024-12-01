package com.acmeplex.movieticketreservation.Service;

import com.acmeplex.movieticketreservation.Model.Notification;
import com.acmeplex.movieticketreservation.Model.User;
import com.acmeplex.movieticketreservation.Repository.NotificationRepository;
import com.acmeplex.movieticketreservation.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public Map<String, Object> createNotification(int userID, String message) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userID));

        Notification notification = new Notification(message, user);
        notificationRepository.save(notification);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }
}
