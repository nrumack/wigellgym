package com.example.WigellGym.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class GymWorkout {
    @Id
    @UuidGenerator
    private UUID workoutId;
    private String name;
    private long participants;
    private long price;
    private Date scheduledAt;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private GymInstructor instructor;

    @OneToMany(mappedBy = "gymWorkout")
    private List<GymBooking> bookings;

    public GymWorkout() {}

    public GymWorkout(UUID workoutId, String name, long participants, long price, GymInstructor instructor, Date scheduledAt) {
        this.workoutId = workoutId;
        this.name = name;
        this.participants = participants;
        this.price = price;
        this.instructor = instructor;
        this.scheduledAt = scheduledAt;
    }

    public UUID getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(UUID workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParticipants() {
        return participants;
    }

    public void setParticipants(long participants) {
        this.participants = participants;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public GymInstructor getInstructor() {
        return instructor;
    }

    public void setInstructor(GymInstructor instructor) {
        this.instructor = instructor;
    }

    public Date getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Date scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public List<GymBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<GymBooking> bookings) {
        this.bookings = bookings;
    }
}
