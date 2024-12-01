package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.Notification;
import com.acmeplex.movieticketreservation.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/")
    public ResponseEntity<?> createNotification(@RequestBody Map<String, Object> payload) {
        try {
            int userID = (int) payload.get("userID");
            String message = (String) payload.get("message");
            Map<String, Object> response = notificationService.createNotification(userID, message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating notification: " + e.getMessage());
        }
    }
}
