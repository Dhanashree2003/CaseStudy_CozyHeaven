package com.hexaware.cozyheaven.hotelbooking.exception;

public class UserIdNotFoundException extends RuntimeException {
    
    public UserIdNotFoundException(Long userId) {
        super("User with ID " + userId + " not found.");
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
