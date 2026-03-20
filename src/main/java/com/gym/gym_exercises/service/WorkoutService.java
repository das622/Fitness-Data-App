package com.gym.gym_exercises.service;

import com.gym.gym_exercises.exception.ResourceNotFoundException;
import com.gym.gym_exercises.model.User;
import com.gym.gym_exercises.model.Workout;
import com.gym.gym_exercises.repository.UserRepository;
import com.gym.gym_exercises.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    final private WorkoutRepository workoutRepository;
    final private UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public List<Workout> getAllWorkouts(Long userId) {
        return workoutRepository.findAllByUserId(userId);
    }
    public Workout createWorkout(Long userId, Workout workout){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found."));
        workout.setUser(user);

        // 3. Ask the WORKOUT repository to save the new workout
        return workoutRepository.save(workout);

    }

    public Workout updateWorkout(Long userId, Long workoutId, Workout workoutDetails) {
        // 1. Verify the user exists
        User userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        // 2. Find the existing workout by its ID
        Workout existingWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Not Found"));

        // 3. THE SECURITY CHECK (Authorization)
        // Does the ID of the user who owns this workout match the ID in the URL?
        if (!existingWorkout.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You do not have permission to edit this workout.");
        }

        // 4. Update the fields
        existingWorkout.setName(workoutDetails.getName());

        // 5. Save and return
        return workoutRepository.save(existingWorkout);
    }
}
