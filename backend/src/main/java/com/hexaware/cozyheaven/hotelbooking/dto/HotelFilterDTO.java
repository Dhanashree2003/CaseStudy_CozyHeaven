package com.hexaware.cozyheaven.hotelbooking.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HotelFilterDTO {
    private String location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean ac;
    
    public boolean isValidDateRange() {
        return checkInDate == null || checkOutDate == null || !checkOutDate.isBefore(checkInDate);
    }

    public boolean isCheckInNotPast() {
        return checkInDate == null || !checkInDate.isBefore(LocalDate.now());
    }

    public boolean isCheckOutNotPast() {
        return checkOutDate == null || !checkOutDate.isBefore(LocalDate.now());
    }
}