package com.example.WigellGym.converters;

import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymWorkout;
import org.springframework.stereotype.Component;

@Component
public class GymWorkoutConverter {

    public GymWorkoutDTO toDto(GymWorkout gymWorkout) {
        GymWorkoutDTO gymWorkoutDTO = new GymWorkoutDTO();
        gymWorkoutDTO.setWorkoutId(gymWorkout.getWorkoutId());
        gymWorkoutDTO.setName(gymWorkout.getName());
        gymWorkoutDTO.setParticipants(gymWorkout.getParticipants());
        gymWorkoutDTO.setPrice(gymWorkout.getPrice());
        gymWorkoutDTO.setScheduledAt(gymWorkout.getScheduledAt());
        gymWorkoutDTO.setInstructorId(gymWorkout.getInstructor() != null ? gymWorkout.getInstructor().getId() : null);
        return gymWorkoutDTO;
    }

    public GymWorkout toEntity(GymWorkoutDTO gymWorkoutDTO) {
        GymWorkout gymWorkout = new GymWorkout();
        gymWorkout.setWorkoutId(gymWorkoutDTO.getWorkoutId());
        gymWorkout.setName(gymWorkoutDTO.getName());
        gymWorkout.setParticipants(gymWorkoutDTO.getParticipants());
        gymWorkout.setPrice(gymWorkoutDTO.getPrice());
        gymWorkout.setScheduledAt(gymWorkoutDTO.getScheduledAt());

        return gymWorkout;
    }
}
