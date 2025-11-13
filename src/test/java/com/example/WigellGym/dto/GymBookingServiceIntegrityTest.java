package com.example.WigellGym.dto;

import com.example.WigellGym.converters.GymBookingConverter;
import com.example.WigellGym.entities.GymBooking;
import com.example.WigellGym.entities.GymWorkout;
import com.example.WigellGym.entities.User;
import com.example.WigellGym.exceptions.AlreadyBookedException;
import com.example.WigellGym.exceptions.BookingLimitReachedException;
import com.example.WigellGym.helpers.CustomPrincipal;
import com.example.WigellGym.repository.GymBookingRepository;
import com.example.WigellGym.repository.GymCustomerRepository;
import com.example.WigellGym.repository.GymWorkoutRepository;
import com.example.WigellGym.services.GymBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GymBookingServiceIntegrityTest {

    @Mock private GymWorkoutRepository gymWorkoutRepository;
    @Mock private GymCustomerRepository gymCustomerRepository;
    @Mock private GymBookingRepository gymBookingRepository;
    @Mock private GymBookingConverter gymBookingConverter;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private GymBookingService gymBookingService;

    private UUID workoutId;
    private UUID customerId;
    private User user;
    private GymWorkout workout;
    private GymBooking booking;
    private BookingResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        workoutId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        user = new User();
        user.setId(customerId);

        workout = new GymWorkout();
        workout.setWorkoutId(workoutId);
        workout.setMaxParticipants(10);
        workout.setPriceSek(100.0);

        booking = new GymBooking(user, workout, LocalDateTime.now());

        expectedResponse = new BookingResponseDTO();
        expectedResponse.setBookingId(booking.getId());
        expectedResponse.setBookedAt(booking.getBookedAt());
        expectedResponse.setPriceEur(new BigDecimal("1.00"));

        // Mock security context
        CustomPrincipal principal = mock(CustomPrincipal.class);
        when(principal.getId()).thenReturn(customerId);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(principal);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldBookWorkoutSuccessfully() {
        when(gymCustomerRepository.findById(customerId)).thenReturn(Optional.of(user));
        when(gymWorkoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(gymBookingRepository.existsByUserAndGymWorkout(user, workout)).thenReturn(false);
        when(gymBookingRepository.countByGymWorkout(workout)).thenReturn(5L);
        when(gymBookingRepository.save(any())).thenReturn(booking);
        when(restTemplate.getForObject(anyString(), eq(BigDecimal.class))).thenReturn(new BigDecimal("1.00"));
        when(gymBookingConverter.toResponseDto(eq(booking), eq(new BigDecimal("1.00")))).thenReturn(expectedResponse);

        BookingResponseDTO result = gymBookingService.bookGymWorkout(workoutId);

        assertEquals(expectedResponse, result);
        verify(gymBookingRepository).save(any());
        verify(gymBookingConverter).toResponseDto(eq(booking), eq(new BigDecimal("1.00")));
    }

    @Test
    void shouldThrowAlreadyBookedException_whenUserHasAlreadyBooked() {
        when(gymCustomerRepository.findById(customerId)).thenReturn(Optional.of(user));
        when(gymWorkoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(gymBookingRepository.existsByUserAndGymWorkout(user, workout)).thenReturn(true);

        assertThrows(AlreadyBookedException.class, () -> gymBookingService.bookGymWorkout(workoutId));
    }

    @Test
    void shouldThrowBookingLimitReachedException_whenWorkoutIsFull() {
        when(gymCustomerRepository.findById(customerId)).thenReturn(Optional.of(user));
        when(gymWorkoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(gymBookingRepository.existsByUserAndGymWorkout(user, workout)).thenReturn(false);
        when(gymBookingRepository.countByGymWorkout(workout)).thenReturn(10L); // full

        assertThrows(BookingLimitReachedException.class, () -> gymBookingService.bookGymWorkout(workoutId));
    }

    @Test
    void shouldThrowEntityNotFoundException_whenWorkoutDoesNotExist() {
        when(gymCustomerRepository.findById(customerId)).thenReturn(Optional.of(user));
        when(gymWorkoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gymBookingService.bookGymWorkout(workoutId));
    }

    @Test
    void shouldThrowEntityNotFoundException_whenCustomerDoesNotExist() {
        when(gymCustomerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gymBookingService.bookGymWorkout(workoutId));
    }
}
