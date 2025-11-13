package com.example.WigellGym.controllers;

import com.example.WigellGym.converters.GymInstructorConverter;
import com.example.WigellGym.dto.GymInstructorDTO;
import com.example.WigellGym.services.GymInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigellgym")
public class GymInstructorController {
    private final GymInstructorService gymInstructorService;

    @Autowired
    public GymInstructorController(GymInstructorService gymInstructorService) {
        this.gymInstructorService = gymInstructorService;
    }

    @GetMapping("/instructors")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<GymInstructorDTO>> getAllInstructors() {
        List<GymInstructorDTO> instructors = gymInstructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @PostMapping("/addinstructor")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<GymInstructorDTO> addInstructor(@RequestBody GymInstructorDTO gymInstructorDTO) {
        GymInstructorDTO newInstructor = gymInstructorService.addInstructor(gymInstructorDTO);
        return ResponseEntity.ok(newInstructor);
    }

}
