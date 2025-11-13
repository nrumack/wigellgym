package com.example.WigellGym.repository;

import com.example.WigellGym.entities.GymBooking;
import com.example.WigellGym.entities.User;
import com.example.WigellGym.entities.GymWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GymBookingRepository extends JpaRepository<GymBooking, UUID> {
    boolean existsByUserAndGymWorkout(User user, GymWorkout gymWorkout);
    long countByGymWorkout(GymWorkout gymWorkout);
    List<GymBooking> findByUser(User user);
    List<GymBooking> findByCanceledTrue();
    List<GymBooking> findByCanceledFalseAndGymWorkout_ScheduledAtBefore(LocalDateTime localDateTime);
    List<GymBooking> findByCanceledFalseAndGymWorkout_ScheduledAtAfter(LocalDateTime localDateTime);

}
