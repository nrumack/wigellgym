package com.example.WigellGym.entities;

import com.example.WigellGym.enums.WorkoutType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class GymInstructor {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private WorkoutType workoutType;

    @OneToMany(mappedBy = "instructor")
    private List<GymWorkout> workouts;

    public GymInstructor() {}

    public GymInstructor(UUID id, String firstName, String lastName, WorkoutType workoutType, List<GymWorkout> workouts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workoutType = workoutType;
        this.workouts = workouts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public WorkoutType getWorkoutType() { return workoutType; }

    public void setWorkoutType(WorkoutType workoutType) { this.workoutType = workoutType; }

    public List<GymWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<GymWorkout> workouts) {
        this.workouts = workouts;
    }
}
