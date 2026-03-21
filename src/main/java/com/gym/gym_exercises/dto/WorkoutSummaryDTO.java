package com.gym.gym_exercises.dto;

import java.util.List;

public class WorkoutSummaryDTO {
    private Long workoutId;
    private String workoutName;
    private List<SetSummaryDTO> sets;

    // Constructors
    public WorkoutSummaryDTO() {}

    public WorkoutSummaryDTO(Long workoutId, String workoutName, List<SetSummaryDTO> sets) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.sets = sets;
    }

    // Getters and Setters
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }
    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }
    public List<SetSummaryDTO> getSets() { return sets; }
    public void setSets(List<SetSummaryDTO> sets) { this.sets = sets; }
}