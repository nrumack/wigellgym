package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.exceptions.InvalidWorkoutException;
import com.example.WigellGym.exceptions.ResourceNotFoundException;
import com.example.WigellGym.repository.GymInstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.WigellGym.repository.GymWorkoutRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GymWorkoutService {
    private final GymWorkoutRepository gymWorkoutRepository;
    private final GymInstructorRepository gymInstructorRepository;
    private final GymWorkoutConverter gymWorkoutConverter;
    Logger log = LoggerFactory.getLogger(GymWorkoutService.class);

    @Autowired
    public GymWorkoutService(GymWorkoutRepository gymWorkoutRepository, GymInstructorRepository gymInstructorRepository, GymWorkoutConverter gymWorkoutConverter) {
        this.gymWorkoutRepository = gymWorkoutRepository;
        this.gymInstructorRepository = gymInstructorRepository;
        this.gymWorkoutConverter = gymWorkoutConverter;
    }

    public List<GymWorkoutDTO> getAllWorkouts() {
    return gymWorkoutRepository.findAll().stream()
            .map(gymWorkoutConverter::toDto)
            .collect(Collectors.toList());
    }

    public GymWorkoutDTO addWorkout(GymWorkoutDTO workoutDTO) {
        GymInstructor instructor = gymInstructorRepository.findById(workoutDTO.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor with ID " + workoutDTO.getInstructorId() + " not found"));

        if (!instructor.getWorkoutType().equals(workoutDTO.getWorkoutType())) {
            throw new InvalidWorkoutException("Instructor is unable teach that class");
        }

        GymWorkout workout = gymWorkoutConverter.toEntity(workoutDTO);
        workout.setInstructor(instructor);
        GymWorkout savedWorkout = gymWorkoutRepository.save(workout);

        log.info("Adding workout with ID {} ", workoutDTO.getWorkoutId());
        return gymWorkoutConverter.toDto(savedWorkout);
    }

    public GymWorkoutDTO updateWorkout(UUID workoutId, GymWorkoutDTO workoutDTO) {
        GymWorkout existingGymWorkout = gymWorkoutRepository.findByWorkoutId(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout with id " + workoutId + " not found"));

        existingGymWorkout.setWorkoutType(workoutDTO.getWorkoutType());
        existingGymWorkout.setMaxParticipants(workoutDTO.getMaxParticipants());
        existingGymWorkout.setPriceSek(workoutDTO.getPriceSek());
        existingGymWorkout.setScheduledAt(workoutDTO.getScheduledAt());

        GymInstructor gymInstructor = gymInstructorRepository.findById(workoutDTO.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor with ID " + workoutDTO.getInstructorId() + " not found"));
        existingGymWorkout.setInstructor(gymInstructor);

        GymWorkout updateGymWorkout = gymWorkoutRepository.save(existingGymWorkout);

        log.info("Updating workout with ID {} ", workoutId);
        return gymWorkoutConverter.toDto(updateGymWorkout);
    }

    public void deleteWorkout(UUID workoutId) {
        if (!gymWorkoutRepository.existsById(workoutId)) {
            throw new ResourceNotFoundException("Workout with id " + workoutId + " not found");
        }
        log.info("Deleting workout with ID {} ", workoutId);
        gymWorkoutRepository.deleteById(workoutId);
    }

}
