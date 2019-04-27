package com.gr232.restaurantadmin.models;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
