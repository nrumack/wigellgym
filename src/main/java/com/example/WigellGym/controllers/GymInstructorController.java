package com.example.WigellGym.controllers;

import com.example.WigellGym.converters.GymInstructorConverter;
import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.entities.GymInstructor;
import com.example.WigellGym.services.GymInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellgym")
public class GymInstructorController {
    private final GymInstructorService gymInstructorService;
    private final GymInstructorConverter gymInstructorConverter;

    @Autowired
    public GymInstructorController(GymInstructorService gymInstructorService, GymInstructorConverter gymInstructorConverter) {
        this.gymInstructorService = gymInstructorService;
        this.gymInstructorConverter = gymInstructorConverter;
    }

    @GetMapping("/instructors")
    public ResponseEntity<List<GymInstructorDTO>> getAllInstructors() {
        List<GymInstructorDTO> instructors = gymInstructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @PostMapping("/addinstructor")
    //@PreAuthorize("hasRole('Admin")
    public ResponseEntity<GymInstructorDTO> addInstructor(@RequestBody GymInstructorDTO gymInstructorDTO) {
        GymInstructor newInstructor = gymInstructorService.addInstructor(gymInstructorDTO);
        return ResponseEntity.ok(gymInstructorConverter.toDto(newInstructor));
    }

}
