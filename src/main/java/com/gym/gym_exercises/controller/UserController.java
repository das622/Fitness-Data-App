package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.model.User;
import com.gym.gym_exercises.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    final private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
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
    @PostMapping("/users/{userId}")
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
