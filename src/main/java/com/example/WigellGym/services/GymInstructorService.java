package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymInstructorConverter;
import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.repository.GymInstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymInstructorService {

    private final GymInstructorRepository gymInstructorRepository;
    private final GymInstructorConverter gymInstructorConverter;

    public GymInstructorService(GymInstructorRepository gymInstructorRepository, GymInstructorConverter gymInstructorConverter) {
        this.gymInstructorRepository = gymInstructorRepository;
        this.gymInstructorConverter = gymInstructorConverter;
    }

    public List<GymInstructorDTO> getAllInstructors() {
        List<GymInstructor> instructors = gymInstructorRepository.findAll();
        return instructors.stream()
                .map(gymInstructorConverter::toDto)
                .collect(Collectors.toList());
    }

    public GymInstructor addInstructor(GymInstructorDTO gymInstructorDTO) {
        GymInstructor gymInstructor = gymInstructorConverter.toEntity(gymInstructorDTO);
        return gymInstructorRepository.save(gymInstructor);
    }
}
