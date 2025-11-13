package com.example.WigellGym.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookingResponseDTO {
    @NotNull
    private UUID bookingId;

    @NotNull
    private LocalDateTime bookedAt;

    private boolean canceled;

    private BigDecimal priceEur;

    private GymWorkoutDTO gymWorkoutDTO;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public BigDecimal getPriceEur() { return priceEur; }

    public void setPriceEur(BigDecimal priceEur) { this.priceEur = priceEur; }

    public GymWorkoutDTO getGymWorkout() {
        return gymWorkoutDTO;
    }

    public void setGymWorkout(GymWorkoutDTO gymWorkoutDTO) {
        this.gymWorkoutDTO = gymWorkoutDTO;
    }
}
