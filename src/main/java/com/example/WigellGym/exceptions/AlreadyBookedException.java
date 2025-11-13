package com.example.WigellGym.exceptions;

public class AlreadyBookedException extends RuntimeException {
    public AlreadyBookedException(String message) {
        super(message);
    }
}
