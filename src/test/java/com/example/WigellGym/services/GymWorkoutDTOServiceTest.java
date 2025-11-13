package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.enums.WorkoutType;
import com.example.WigellGym.repository.GymInstructorRepository;
import com.example.WigellGym.repository.GymWorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GymWorkoutDTOServiceTest {

    @Mock private GymInstructorRepository gymInstructorRepository;
    @Mock private GymWorkoutRepository gymWorkoutRepository;
    @Mock private GymWorkoutConverter gymWorkoutConverter;

    @InjectMocks private GymWorkoutService gymWorkoutService;

    @Test
    void addWorkout_shouldAddGymWorkout() {
        // Arrange
        UUID instructorId = UUID.randomUUID();

        GymInstructor gymInstructor = new GymInstructor();
        gymInstructor.setId(instructorId);
        gymInstructor.setWorkoutType(WorkoutType.CADRIO);

        GymWorkoutDTO inputDto = new GymWorkoutDTO();
        inputDto.setInstructorId(instructorId);
        inputDto.setWorkoutType(WorkoutType.CADRIO);
        inputDto.setMaxParticipants(10L);
        inputDto.setPriceSek(150.0);
        inputDto.setScheduledAt(LocalDateTime.now().plusDays(1));

        GymWorkout entity = new GymWorkout();
        entity.setInstructor(gymInstructor);
        entity.setWorkoutType(inputDto.getWorkoutType());
        entity.setMaxParticipants(inputDto.getMaxParticipants());
        entity.setPriceSek(inputDto.getPriceSek());
        entity.setScheduledAt(inputDto.getScheduledAt());

        GymWorkout savedEntity = new GymWorkout();
        savedEntity.setWorkoutId(UUID.randomUUID());
        savedEntity.setInstructor(gymInstructor);
        savedEntity.setWorkoutType(inputDto.getWorkoutType());
        savedEntity.setMaxParticipants(inputDto.getMaxParticipants());
        savedEntity.setPriceSek(inputDto.getPriceSek());
        savedEntity.setScheduledAt(inputDto.getScheduledAt());

        GymWorkoutDTO expectedDto = new GymWorkoutDTO();
        expectedDto.setWorkoutId(savedEntity.getWorkoutId());
        expectedDto.setInstructorId(instructorId);
        expectedDto.setWorkoutType(inputDto.getWorkoutType());
        expectedDto.setMaxParticipants(inputDto.getMaxParticipants());
        expectedDto.setPriceSek(inputDto.getPriceSek());
        expectedDto.setScheduledAt(inputDto.getScheduledAt());

        when(gymInstructorRepository.findById(instructorId)).thenReturn(Optional.of(gymInstructor));
        when(gymWorkoutConverter.toEntity(inputDto)).thenReturn(entity);
        when(gymWorkoutRepository.save(entity)).thenReturn(savedEntity);
        when(gymWorkoutConverter.toDto(savedEntity)).thenReturn(expectedDto);

        // Act
        GymWorkoutDTO result = gymWorkoutService.addWorkout(inputDto);

        // Assert
        assertEquals(expectedDto, result);
        verify(gymInstructorRepository).findById(instructorId);
        verify(gymWorkoutConverter).toEntity(inputDto);
        verify(gymWorkoutRepository).save(entity);
        verify(gymWorkoutConverter).toDto(savedEntity);
    }
}
