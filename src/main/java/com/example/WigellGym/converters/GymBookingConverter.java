package com.example.WigellGym.converters;

import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.entities.GymBooking;
import org.springframework.stereotype.Component;


@Component
public class GymBookingConverter {
    private final GymWorkoutConverter gymWorkoutConverter;

    public GymBookingConverter(GymWorkoutConverter gymWorkoutConverter) {
        this.gymWorkoutConverter = gymWorkoutConverter;
    }

    public BookingResponseDTO toResponseDto(GymBooking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getId());
        bookingResponseDTO.setBookedAt(booking.getBookedAt());
        bookingResponseDTO.setGymWorkout(gymWorkoutConverter.toDto(booking.getGymWorkout()));
        return bookingResponseDTO;
    }


}
