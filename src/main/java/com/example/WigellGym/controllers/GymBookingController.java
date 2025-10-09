package com.example.WigellGym.controllers;

import com.example.WigellGym.converters.GymBookingConverter;
import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymBooking;
import com.example.WigellGym.services.GymBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wigellgym")
public class GymBookingController {

    private final GymBookingService gymBookingService;
    private final GymBookingConverter gymBookingConverter;

    @Autowired
    public GymBookingController(GymBookingService gymBookingService, GymBookingConverter gymBookingConverter) {
        this.gymBookingService = gymBookingService;
        this.gymBookingConverter = gymBookingConverter;
    }

    @PostMapping("/bookworkout/{workoutId}")
    public ResponseEntity<BookingResponseDTO> bookWorkout(@PathVariable UUID workoutId) {
        BookingResponseDTO response = gymBookingService.bookGymWorkout(workoutId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping ("/mybookings")
    //customers previous bookings
    public ResponseEntity<List<GymWorkoutDTO>> getAllPreviousBookings() {
        return ResponseEntity.ok(gymBookingService.getAllPreviousWorkouts());
    }

    @PutMapping("/cancelworkout/{id}")
    //customer cancels workout atleast 24 hours ahead
    public ResponseEntity<BookingResponseDTO> cancelWorkout(@PathVariable UUID id) {
        BookingResponseDTO canceled = gymBookingService.cancelWorkout(id);
        return ResponseEntity.ok(canceled);
    }

    @GetMapping("/listcanceled")
    //admin sees all the cancelled booked workouts
    public ResponseEntity<List<BookingResponseDTO>> getAllCanceledBookings() {
        return ResponseEntity.ok(gymBookingService.listCanceledWorkouts());
    }

    @GetMapping("/listupcoming")
    //Admin lists all upcoming booked workouts
    public ResponseEntity<List<BookingResponseDTO>> getAllUpcomingBookings() {
        return ResponseEntity.ok(gymBookingService.listUpcomingWorkouts());
    }

    @GetMapping("/listpastbookings")
    //admin is able to see booked workouts that have already happened
    public ResponseEntity<List<BookingResponseDTO>> getAllPastBookings() {
        return ResponseEntity.ok(gymBookingService.listPastWorkouts());
    }
}
