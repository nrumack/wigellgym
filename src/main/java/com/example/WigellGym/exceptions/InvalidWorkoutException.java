package com.example.WigellGym.exceptions;

public class InvalidWorkoutException extends RuntimeException {
    public InvalidWorkoutException(String message) {
        super(message);
    }
}
