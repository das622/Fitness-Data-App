package com.gym.gym_exercises.repository;

import com.gym.gym_exercises.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // In UserRepository.java — add this line:
    Optional<User> findByEmail(String email);
}
