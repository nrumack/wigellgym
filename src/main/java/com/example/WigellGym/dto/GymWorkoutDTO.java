package com.example.WigellGym.dto;

import com.example.WigellGym.enums.WorkoutType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class GymWorkoutDTO {
    private UUID workoutId;

    private WorkoutType workoutType;

    @NotNull
    private Long maxParticipants;

    @NotNull
    private double priceSek;

    private double priceEur;

    @NotNull
    private UUID instructorId;

    @NotNull
    private LocalDateTime scheduledAt;

    public UUID getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(UUID workoutId) {
        this.workoutId = workoutId;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public Long getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Long maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getPriceSek() {
        return priceSek;
    }

    public void setPriceSek(double priceSek) {
        this.priceSek = priceSek;
    }

    public double getPriceEur() { return priceEur; }

    public void setPriceEur(double priceEur) { this.priceEur = priceEur; }

    public UUID getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
