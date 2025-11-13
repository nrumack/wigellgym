package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymInstructorConverter;
import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.repository.GymInstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymInstructorService {

    private final GymInstructorRepository gymInstructorRepository;
    private final GymInstructorConverter gymInstructorConverter;

    private final Logger log = LoggerFactory.getLogger(GymInstructorService.class);

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

    public GymInstructorDTO addInstructor(GymInstructorDTO gymInstructorDTO) {
        GymInstructor gymInstructor = gymInstructorRepository.save(
                gymInstructorConverter.toEntity(gymInstructorDTO)
        );
        log.info("Gym instructor added: {}", gymInstructor.getFirstName());
        return gymInstructorConverter.toDto(gymInstructor);
    }
}
