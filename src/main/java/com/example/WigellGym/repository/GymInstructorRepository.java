package com.example.WigellGym.repository;

import com.example.WigellGym.entities.GymInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GymInstructorRepository extends JpaRepository<GymInstructor, UUID> {
}
