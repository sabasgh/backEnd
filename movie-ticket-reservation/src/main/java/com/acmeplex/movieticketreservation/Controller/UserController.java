package com.acmeplex.movieticketreservation.Controller;

import com.acmeplex.movieticketreservation.Model.RegisteredUser;
import com.acmeplex.movieticketreservation.Model.User;
import com.acmeplex.movieticketreservation.Service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisteredUser user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();

            // Validate email and password
            if (email == null || password == null) {
                return ResponseEntity.badRequest().body("Email and password are required.");
            }
            Map<String, Object> response = userService.login(email, password);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON input");
        }
    }

    @GetMapping("/{userID}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userID) {
        try {
            Map<String, Object> response = userService.getUserProfile(userID);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user profile: " + e.getMessage());
        }
    }

    @PutMapping("/{userID}")
    public ResponseEntity<?> updateUserProfile(@PathVariable int userID, @RequestBody Map<String, Object> userDetails) {
        try {
           RegisteredUser updatedUser = userService.updateUserProfile(userID, userDetails);
            return ResponseEntity.ok(Map.of("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
