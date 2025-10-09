package com.example.WigellGym.services;

import com.example.WigellGym.converters.GymBookingConverter;
import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymBooking;
import com.example.WigellGym.entities.User;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.repository.GymBookingRepository;
import com.example.WigellGym.repository.GymCustomerRepository;
import com.example.WigellGym.repository.GymWorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.WigellGym.security.CustomPrincipal;


import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GymBookingService {
    private final GymWorkoutRepository gymWorkoutRepository;
    private final GymCustomerRepository gymCustomerRepository;
    private final GymBookingRepository gymBookingRepository;
    private final GymWorkoutConverter gymWorkoutConverter;
    private final GymBookingConverter gymBookingConverter;

    @Autowired
    public GymBookingService(GymWorkoutRepository gymWorkoutRepository, GymCustomerRepository gymCustomerRepository, GymBookingRepository gymBookingRepository, GymWorkoutConverter gymWorkoutConverter, GymBookingConverter gymBookingConverter) {
        this.gymWorkoutRepository = gymWorkoutRepository;
        this.gymCustomerRepository = gymCustomerRepository;
        this.gymBookingRepository = gymBookingRepository;
        this.gymWorkoutConverter = gymWorkoutConverter;
        this.gymBookingConverter = gymBookingConverter;
    }

    public List<GymWorkoutDTO> getAllPreviousWorkouts() {
        UUID customerId = ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = gymCustomerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));


        List<GymBooking> gymBookings = gymBookingRepository.findByUser(user);

        return gymBookings.stream()
                .map(GymBooking::getGymWorkout)
                .map(gymWorkoutConverter::toDto)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO bookGymWorkout(UUID workoutId) {
        UUID customerId = ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = gymCustomerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));


        GymWorkout workout = gymWorkoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        boolean alreadyBooked = gymBookingRepository.existsByUserAndGymWorkout(user, workout);
        if (alreadyBooked) {
            throw new IllegalStateException("You have already booked this workout");
        }

        GymBooking gymBooking = new GymBooking(user, workout, new Date());
        GymBooking savedBooking = gymBookingRepository.save(gymBooking);

        return gymBookingConverter.toResponseDto(savedBooking);
    }


    public BookingResponseDTO cancelWorkout(UUID bookingId) {
        GymBooking booking = gymBookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        Date now = new Date();
        Date workoutTime = booking.getGymWorkout().getScheduledAt();

        long hoursUntilWorkout = Duration.between(now.toInstant(), workoutTime.toInstant()).toHours();
        if (hoursUntilWorkout <= 24) {
            throw new IllegalStateException("Cannot cancel less than 24 hours before workout");
        }

        booking.setCanceled(true);
        gymBookingRepository.save(booking);
        return gymBookingConverter.toResponseDto(booking);
    }

    public List<BookingResponseDTO> listCanceledWorkouts() {
        return gymBookingRepository.findByCanceledTrue().stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> listUpcomingWorkouts() {
        Date now = new Date();
        return gymBookingRepository.findByCanceledFalseAndGymWorkout_ScheduledAtAfter(now).stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> listPastWorkouts() {
        Date now = new Date();
        return gymBookingRepository.findByCanceledFalseAndGymWorkout_ScheduledAtBefore(now).stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

}
