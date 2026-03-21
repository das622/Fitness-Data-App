package com.gym.gym_exercises.repository;

import com.gym.gym_exercises.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    void deleteByName(String name);
    Optional<Exercise> findByNameIgnoreCase(String name);

    Optional<Exercise> findByName(String name);

    List<Exercise> findByCategory(String category);

    // Replaces the stream filters for exact or partial equipment matches
    List<Exercise> findByEquipmentContainingIgnoreCase(String equipment);

    // Replaces the stream filters for partial name searches
    List<Exercise> findByNameContainingIgnoreCase(String name);

    // Replaces the stream filters for primary muscles
    List<Exercise> findByPrimaryMusclesContainingIgnoreCase(String muscle);
}