package com.example.WigellGym.converters;

import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.entities.GymInstructor;
import org.springframework.stereotype.Component;

@Component
public class GymInstructorConverter {

    public GymInstructorDTO toDto(GymInstructor gymInstructor) {
        GymInstructorDTO dto = new GymInstructorDTO();
        dto.setInstructorId(gymInstructor.getId());
        dto.setFirstName(gymInstructor.getFirstName());
        dto.setLastName(gymInstructor.getLastName());
        dto.setWorkoutType(gymInstructor.getWorkoutType());
        return dto;
    }

    public GymInstructor toEntity(GymInstructorDTO dto) {
        GymInstructor gymInstructor = new GymInstructor();
        gymInstructor.setId(dto.getInstructorId());
        gymInstructor.setFirstName(dto.getFirstName());
        gymInstructor.setLastName(dto.getLastName());
        gymInstructor.setWorkoutType(dto.getWorkoutType());
        return gymInstructor;
    }
}
