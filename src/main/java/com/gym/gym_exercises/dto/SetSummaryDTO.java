package com.gym.gym_exercises.dto;

public class SetSummaryDTO {
    private String exerciseName;
    private int reps;
    private double weight;

    // Constructors
    public SetSummaryDTO() {}

    public SetSummaryDTO(String exerciseName, int reps, double weight) {
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters and Setters
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}