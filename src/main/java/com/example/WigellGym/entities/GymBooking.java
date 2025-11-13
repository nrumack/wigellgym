package com.example.WigellGym.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GymBooking {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "gym_customer_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private GymWorkout gymWorkout;

    private LocalDateTime scheduledAt;

    private boolean canceled = false;

    public GymBooking() {}

    public GymBooking(User user, GymWorkout gymWorkout, LocalDateTime scheduledAt) {
        this.user = user;
        this.gymWorkout = gymWorkout;
        this.scheduledAt = scheduledAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getGymCustomer() {
        return user;
    }

    public void setGymCustomer(User user) {
        this.user = user;
    }

    public GymWorkout getGymWorkout() {
        return gymWorkout;
    }

    public void setGymWorkout(GymWorkout gymWorkout) {
        this.gymWorkout = gymWorkout;
    }

    public LocalDateTime getBookedAt() {
        return scheduledAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.scheduledAt = bookedAt;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
