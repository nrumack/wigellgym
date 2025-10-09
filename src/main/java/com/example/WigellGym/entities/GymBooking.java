package com.example.WigellGym.entities;

import jakarta.persistence.*;

import java.util.Date;
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

    @Temporal(TemporalType.DATE)
    private Date scheduledAt;

    private boolean canceled = false;

    public GymBooking() {}

    public GymBooking(User user, GymWorkout gymWorkout, Date scheduledAt) {
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

    public Date getBookedAt() {
        return scheduledAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.scheduledAt = bookedAt;
    }

    public Date getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Date scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
