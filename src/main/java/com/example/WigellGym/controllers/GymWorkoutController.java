package com.example.WigellGym.controllers;

import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.services.GymWorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wigellgym")
public class GymWorkoutController {
    private final GymWorkoutService gymWorkoutService;

    @Autowired
    public GymWorkoutController(GymWorkoutService gymWorkoutService) {
        this.gymWorkoutService = gymWorkoutService;
    }
    //Customer
    @GetMapping("/workouts")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<List<GymWorkoutDTO>> getAllWorkouts() {
        return ResponseEntity.ok(gymWorkoutService.getAllWorkouts());
    }


    @PostMapping("/addworkout")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<GymWorkoutDTO> addWorkout(@Valid @RequestBody GymWorkoutDTO workoutDTO) {
        GymWorkoutDTO saved = gymWorkoutService.addWorkout(workoutDTO);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/updateworkout/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<GymWorkoutDTO> updateWorkout(@PathVariable UUID id, @Valid @RequestBody GymWorkoutDTO gymWorkoutDTO) {
        GymWorkoutDTO updated = gymWorkoutService.updateWorkout(id, gymWorkoutDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/remworkout/{id}")
    @PreAuthorize("hasRole('Admin')")
    private ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        gymWorkoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
