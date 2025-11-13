package com.example.WigellGym.converters;

import com.example.WigellGym.dto.BookingResponseDTO;
import com.example.WigellGym.entities.GymBooking;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class GymBookingConverter {
    private final GymWorkoutConverter gymWorkoutConverter;

    public GymBookingConverter(GymWorkoutConverter gymWorkoutConverter) {
        this.gymWorkoutConverter = gymWorkoutConverter;
    }

    public BookingResponseDTO toResponseDto(GymBooking booking, BigDecimal priceEur) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getId());
        bookingResponseDTO.setBookedAt(booking.getBookedAt());
        bookingResponseDTO.setGymWorkout(gymWorkoutConverter.toDto(booking.getGymWorkout()));
        bookingResponseDTO.setPriceEur(priceEur);
        return bookingResponseDTO;
    }

    public BookingResponseDTO toResponseDto(GymBooking booking) {
        return toResponseDto(booking, BigDecimal.ZERO);
    }
}
