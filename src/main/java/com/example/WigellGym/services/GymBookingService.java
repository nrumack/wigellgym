package com.example.WigellGym.services;

import com.example.WigellGym.entities.GymWorkout;
import org.springframework.web.client.RestTemplate;
import com.example.WigellGym.converters.GymBookingConverter;
import com.example.WigellGym.converters.GymWorkoutConverter;
import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.dto.GymWorkoutDTO;
import com.example.WigellGym.entities.GymBooking;
import com.example.WigellGym.entities.User;
import com.example.WigellGym.exceptions.AlreadyBookedException;
import com.example.WigellGym.exceptions.BookingLimitReachedException;
import com.example.WigellGym.repository.GymBookingRepository;
import com.example.WigellGym.repository.GymCustomerRepository;
import com.example.WigellGym.repository.GymWorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.WigellGym.helpers.CustomPrincipal;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(GymBookingService.class);

    @Autowired
    public GymBookingService(GymWorkoutRepository gymWorkoutRepository, GymCustomerRepository gymCustomerRepository, GymBookingRepository gymBookingRepository, GymWorkoutConverter gymWorkoutConverter, GymBookingConverter gymBookingConverter, RestTemplate restTemplate) {
        this.gymWorkoutRepository = gymWorkoutRepository;
        this.gymCustomerRepository = gymCustomerRepository;
        this.gymBookingRepository = gymBookingRepository;
        this.gymWorkoutConverter = gymWorkoutConverter;
        this.gymBookingConverter = gymBookingConverter;
        this.restTemplate = restTemplate;
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
            throw new AlreadyBookedException("You have already booked this workout");
        }

        long currentBookings = gymBookingRepository.countByGymWorkout(workout);
        if (currentBookings >= workout.getMaxParticipants()) {
            throw new BookingLimitReachedException("Workout is fully booked");
        }

        GymBooking gymBooking = new GymBooking(user, workout, LocalDateTime.now());

        BigDecimal priceEur = restTemplate.getForObject(
                "http://wigellgateway:4545/api/currency/convert?sek=" + workout.getPriceSek(),
                BigDecimal.class
        );

        GymBooking savedBooking = gymBookingRepository.save(gymBooking);
        log.info("User {} booked workout at {} at {}", user.getId(), workoutId, LocalDateTime.now());

        return gymBookingConverter.toResponseDto(savedBooking, priceEur);
    }

    public BookingResponseDTO cancelWorkout(UUID bookingId) {
        GymBooking booking = gymBookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime workoutTime = booking.getGymWorkout().getScheduledAt();

        long hoursUntilWorkout = Duration.between(now, workoutTime).toHours();
        if (hoursUntilWorkout <= 24) {
            throw new IllegalStateException("Cannot cancel less than 24 hours before workout");
        }

        booking.setCanceled(true);
        gymBookingRepository.save(booking);

        log.info("Workout {} cancelled", bookingId);

        return gymBookingConverter.toResponseDto(booking);
    }

    public List<BookingResponseDTO> listCanceledWorkouts() {
        return gymBookingRepository.findByCanceledTrue().stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> listUpcomingWorkouts() {
        LocalDateTime now = LocalDateTime.now();
        return gymBookingRepository.findByCanceledFalseAndGymWorkout_ScheduledAtAfter(now).stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> listPastWorkouts() {
        LocalDateTime now = LocalDateTime.now();
        return gymBookingRepository.findByCanceledFalseAndGymWorkout_ScheduledAtBefore(now).stream()
                .map(gymBookingConverter::toResponseDto)
                .collect(Collectors.toList());
    }

}
