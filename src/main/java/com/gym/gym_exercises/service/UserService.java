package com.gym.gym_exercises.service;

import com.gym.gym_exercises.exception.GlobalExceptionHandler;
import com.gym.gym_exercises.exception.ResourceNotFoundException;
import com.gym.gym_exercises.model.User;
import com.gym.gym_exercises.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        userRepository.save(user);
        return user;
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or you do not have permission to access"));
    }

    public User updateUserById(Long userId, User updatedUser){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or you do not have permission to access"));
        user.setEmail(updatedUser.getEmail());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPassword(updatedUser.getPassword());

        return user;
    }

    public void deleteUser(Long userId){
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or you do not have permission to access"));

        userRepository.delete(userToDelete);
    }
}
