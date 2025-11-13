package com.example.WigellGym.exceptions;

public class BookingLimitReachedException extends RuntimeException {
    public BookingLimitReachedException(String message) {
        super(message);
    }
}
