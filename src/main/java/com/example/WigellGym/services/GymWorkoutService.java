package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.repository.GymInstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.WigellGym.repository.GymWorkoutRepository;

import java.util.List;
import java.util.UUID;

@Service
public class GymWorkoutService {
    private final GymWorkoutRepository gymWorkoutRepository;
    private final GymInstructorRepository gymInstructorRepository;
    private final GymWorkoutConverter gymWorkoutConverter;

    @Autowired
    public GymWorkoutService(GymWorkoutRepository gymWorkoutRepository, GymInstructorRepository gymInstructorRepository, GymWorkoutConverter gymWorkoutConverter) {
        this.gymWorkoutRepository = gymWorkoutRepository;
        this.gymInstructorRepository = gymInstructorRepository;
        this.gymWorkoutConverter = gymWorkoutConverter;
    }

    public List<GymWorkout> getAllWorkouts() {
        List<GymWorkout> workouts = gymWorkoutRepository.findAll();
    return workouts;
    }

    public GymWorkout addWorkout(GymWorkoutDTO workoutDTO) {
        GymInstructor instructor = gymInstructorRepository.findById(workoutDTO.getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        GymWorkout workout = gymWorkoutConverter.toEntity(workoutDTO);
        workout.setInstructor(instructor);

        return gymWorkoutRepository.save(workout);
    }

    public GymWorkoutDTO updatedWorkout(UUID workoutId, GymWorkoutDTO workoutDTO) {
        GymWorkout existingGymWorkout = gymWorkoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        existingGymWorkout.setName(workoutDTO.getName());
        existingGymWorkout.setParticipants(workoutDTO.getParticipants());
        existingGymWorkout.setPrice(workoutDTO.getPrice());
        existingGymWorkout.setScheduledAt(workoutDTO.getScheduledAt());

        GymInstructor gymInstructor = gymInstructorRepository.findById(workoutDTO.getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
        existingGymWorkout.setInstructor(gymInstructor);

        GymWorkout updatedGymWorkout = gymWorkoutRepository.save(existingGymWorkout);

        GymWorkoutDTO updatedGymWorkoutDTO = new GymWorkoutDTO();
        updatedGymWorkoutDTO.setWorkoutId(updatedGymWorkout.getWorkoutId());
        updatedGymWorkoutDTO.setName(updatedGymWorkout.getName());
        updatedGymWorkoutDTO.setParticipants(updatedGymWorkout.getParticipants());
        updatedGymWorkoutDTO.setPrice(updatedGymWorkout.getPrice());
        updatedGymWorkoutDTO.setScheduledAt(updatedGymWorkout.getScheduledAt());
        updatedGymWorkoutDTO.setInstructorId(updatedGymWorkout.getInstructor().getId());

        return updatedGymWorkoutDTO;
    }


    public void deleteWorkout(UUID workoutId) {
        GymWorkout workout = gymWorkoutRepository.findByWorkoutId(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        gymWorkoutRepository.delete(workout);
    }

}
