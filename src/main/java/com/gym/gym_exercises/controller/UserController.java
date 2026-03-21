package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.dto.LoginRequest;
import com.gym.gym_exercises.model.User;
import com.gym.gym_exercises.repository.UserRepository;
import com.gym.gym_exercises.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {"https://gym-frontend-one-gamma.vercel.app", "http://localhost:5174"})
@RestController
@RequestMapping("/api/v1")
public class UserController {
    final private UserService userService;
    final private UserRepository userRepository; // ADD THIS

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository; // ADD THIS
    }

    // Add to UserController.java
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = userOpt.get();
        // NOTE: passwords should be hashed with BCrypt in production!
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok(user);
    }

    // CREATE USER
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User userCreated = userService.createUser(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    // READ USER
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // UPDATE USER
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails){
        User updatedUser = userService.updateUserById(userId, userDetails);
        return ResponseEntity.ok(updatedUser);

    }

    // DELETE USER
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
