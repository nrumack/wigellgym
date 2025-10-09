package com.example.WigellGym.dto;

import java.util.Date;
import java.util.UUID;

public class BookingResponseDTO {
    private UUID bookingId;
    private Date bookedAt;
    private boolean canceled;

    private GymWorkoutDTO gymWorkout;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public GymWorkoutDTO getGymWorkout() {
        return gymWorkout;
    }

    public void setGymWorkout(GymWorkoutDTO gymWorkout) {
        this.gymWorkout = gymWorkout;
    }
}
