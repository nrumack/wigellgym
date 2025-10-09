package com.example.WigellGym.repository;

import com.example.WigellGym.entities.GymWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, UUID> {
    List<GymWorkout> findAll();

    Optional<GymWorkout> findByWorkoutId(UUID workoutId);
}
