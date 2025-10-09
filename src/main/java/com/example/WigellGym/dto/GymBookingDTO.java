package com.example.WigellGym.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class GymBookingDTO {
    private UUID id;
    @NotNull
    private UUID workoutId;

    @NotNull
    private UUID customerId;

    private Date bookedAt;

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

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }
}
