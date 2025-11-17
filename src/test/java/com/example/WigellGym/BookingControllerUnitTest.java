package com.example.WigellGym;

import com.example.WigellGym.controllers.GymBookingController;
import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.exceptions.AlreadyBookedException;
import com.example.WigellGym.exceptions.BookingLimitReachedException;
import com.example.WigellGym.services.GymBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GymBookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerUnitTest {

    @Autowired private MockMvc mockMvc;

    @Mock private GymBookingService gymBookingService;

    private UUID workoutId;
    private BookingResponseDTO expectedResponse;

    @BeforeEach
    void setUp(@Autowired GymBookingController controller) {
        ReflectionTestUtils.setField(controller, "gymBookingService", gymBookingService);

        workoutId = UUID.randomUUID();

        expectedResponse = new BookingResponseDTO();
        expectedResponse.setBookingId(UUID.randomUUID());
        expectedResponse.setBookedAt(LocalDateTime.now());
        expectedResponse.setPriceEur(new BigDecimal("1.00"));
    }

    @Test
    void shouldBookWorkoutSuccessfully() throws Exception {
        when(gymBookingService.bookGymWorkout(workoutId)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/bookings/bookworkout/{workoutId}", workoutId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(expectedResponse.getBookingId().toString()))
                .andExpect(jsonPath("$.priceEur").value("1.00"));

        verify(gymBookingService).bookGymWorkout(workoutId);
    }

    @Test
    void shouldReturnConflict_whenAlreadyBooked() throws Exception {
        when(gymBookingService.bookGymWorkout(workoutId))
                .thenThrow(new AlreadyBookedException("Already booked"));

        mockMvc.perform(post("/api/bookings/bookworkout/{workoutId}", workoutId))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnBadRequest_whenWorkoutFull() throws Exception {
        when(gymBookingService.bookGymWorkout(workoutId))
                .thenThrow(new BookingLimitReachedException("Workout full"));

        mockMvc.perform(post("/api/bookings/bookworkout/{workoutId}", workoutId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFound_whenWorkoutDoesNotExist() throws Exception {
        when(gymBookingService.bookGymWorkout(workoutId))
                .thenThrow(new EntityNotFoundException("Workout not found"));

        mockMvc.perform(post("/api/bookings/bookworkout/{workoutId}", workoutId))
                .andExpect(status().isNotFound());
    }
}
