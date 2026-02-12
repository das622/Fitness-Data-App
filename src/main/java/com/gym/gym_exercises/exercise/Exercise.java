package com.gym.gym_exercises.exercise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="exercises")

public class Exercise {


    @Id
    @Column(name="id",unique=true)
    private String id;

    private String name;

    private String force;

    private String level;

    private String mechanic;

    private String equipment;

    private String primary_muscles;

    private String category;

    public Exercise() {
    }

    public Exercise(String id, String name, String force, String level, String mechanic, String equipment, String primary_muscles, String category) {
        this.id = id;
        this.name = name;
        this.force = force;
        this.level = level;
        this.mechanic = mechanic;
        this.equipment = equipment;
        this.primary_muscles = primary_muscles;
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

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getPrimary_muscles() {
        return primary_muscles;
    }

    public void setPrimary_muscles(String primary_muscles) {
        this.primary_muscles = primary_muscles;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
