package com.example.WigellGym.controllers;

import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.services.GymWorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wigellgym")
public class GymWorkoutController {
    private final GymWorkoutService gymWorkoutService;
    private final GymWorkoutConverter workoutConverter;

    @Autowired
    public GymWorkoutController(GymWorkoutService gymWorkoutService, GymWorkoutConverter gymWorkoutConverter) {
        this.gymWorkoutService = gymWorkoutService;
        this.workoutConverter = gymWorkoutConverter;
    }

    @GetMapping("/workouts")
    public ResponseEntity<List<GymWorkoutDTO>> getAllWorkouts() {
        List<GymWorkout> workouts = gymWorkoutService.getAllWorkouts();
        List<GymWorkoutDTO> workoutDto = workouts.stream().map(workoutConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(workoutDto);
    }


    @PostMapping("/addworkout")
// @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<GymWorkoutDTO> addWorkout(@Valid @RequestBody GymWorkoutDTO workoutDTO) {
        GymWorkout saved = gymWorkoutService.addWorkout(workoutDTO);
        return ResponseEntity.ok(workoutConverter.toDto(saved));
    }

    @PutMapping("/updateworkout/{id}")
    public ResponseEntity<GymWorkoutDTO> updateWorkout(@PathVariable UUID id, @Valid @RequestBody GymWorkoutDTO gymWorkoutDTO) {
        GymWorkoutDTO updated = gymWorkoutService.updatedWorkout(id, gymWorkoutDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/remworkout/{id}")
    private ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        gymWorkoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
