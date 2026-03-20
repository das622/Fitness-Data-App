package com.gym.gym_exercises.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Added this import

@Entity
@Table(name="exercises")
@JsonIgnoreProperties(ignoreUnknown = true) // Tells Spring Boot to ignore 'level' and 'force' in the JSON
public class Exercise {

    @Id
    @Column(name="id", unique=true)
    private String id;

    private String name;

    private String mechanic;

    private String equipment;
    @JsonProperty("primary_muscles")
    @Column(name = "primary_muscles") // Keeps the database column name the same
    private String primaryMuscles;

    private String category;

    public Exercise() {
    }

    // Removed force and level from this constructor
    public Exercise(String id, String name, String mechanic, String equipment, String primaryMuscles, String category) {
        this.id = id;
        this.name = name;
        this.mechanic = mechanic;
        this.equipment = equipment;
        this.primaryMuscles = primaryMuscles;
        this.category = category;
    }

    public Exercise(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPrimaryMuscles() {
        return primaryMuscles;
    }

    public void setPrimary_muscles(String primary_muscles) {
        this.primaryMuscles = primaryMuscles;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}