package com.example.WigellGym.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class GymInstructorDTO {
    private UUID instructorId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String specialization;

    public UUID getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
