package com.example.WigellGym.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class GymBookingDTO {
    private UUID id;
    @NotNull
    private UUID workoutId;

    @NotNull
    private UUID customerId;

    private LocalDateTime bookedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(UUID workoutId) {
        this.workoutId = workoutId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
