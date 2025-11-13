package com.example.WigellGym.entities;

import com.example.WigellGym.enums.WorkoutType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class GymWorkout {
    @Id
    @UuidGenerator
    private UUID workoutId;

    @Enumerated(EnumType.STRING)
    private WorkoutType workoutType;

    @Min(1)
    private long maxParticipants;

    @DecimalMin("0.0")
    private double priceEur;

    private LocalDateTime scheduledAt;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private GymInstructor instructor;

    @OneToMany(mappedBy = "gymWorkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GymBooking> bookings;

    public GymWorkout() {}

    public GymWorkout(UUID workoutId, WorkoutType workoutType, long maxParticipants, double priceSek, GymInstructor instructor, LocalDateTime scheduledAt) {
        this.workoutId = workoutId;
        this.workoutType = workoutType;
        this.maxParticipants = maxParticipants;
        this.priceEur = priceSek;
        this.instructor = instructor;
        this.scheduledAt = scheduledAt;
    }

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

    public long getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(long maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getPriceSek() {
        return priceEur;
    }

    public void setPriceSek(double price) {
        this.priceEur = price;
    }

    public double getPriceEur() { return priceEur; }

    public void setPriceEur(double priceEur) { this.priceEur = priceEur; }

    public GymInstructor getInstructor() {
        return instructor;
    }

    public void setInstructor(GymInstructor instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public List<GymBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<GymBooking> bookings) {
        this.bookings = bookings;
    }
}
