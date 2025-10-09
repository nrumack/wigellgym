package com.example.WigellGym.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class GymInstructor {
    @Id
    @UuidGenerator
    private UUID id;

    private String firstName;
    private String lastName;
    private String specialization;

    @OneToMany(mappedBy = "instructor")
    private List<GymWorkout> workouts;

    public GymInstructor() {}

    public GymInstructor(UUID id, String firstName, String lastName, String specialization, List<GymWorkout> workouts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
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

    public String getSpecialization() { return specialization; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public List<GymWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<GymWorkout> workouts) {
        this.workouts = workouts;
    }
}
