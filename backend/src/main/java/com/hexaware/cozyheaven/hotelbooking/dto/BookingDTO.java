package com.hexaware.cozyheaven.hotelbooking.dto;

import java.time.LocalDate;

import com.hexaware.cozyheaven.hotelbooking.entity.enums.BookingStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class BookingDTO {
	
	private Long bookingID;
    private Long userID;
    private Long hotelID;
    private Long roomID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int noOfAdults;
    private int noOfChildren;
    private double totalFare;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
