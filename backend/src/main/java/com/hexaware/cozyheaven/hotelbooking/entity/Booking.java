package com.hexaware.cozyheaven.hotelbooking.entity;

import java.time.LocalDate;

import com.hexaware.cozyheaven.hotelbooking.entity.enums.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user; // Mapping to User entity
    
    @ManyToOne
    @JoinColumn(name = "hotelID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "RoomID")
    private Room room; // Mapping to Room entity
    
    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @Min(value = 1)
    private int noOfAdults;

    @Min(value = 0)
    private int noOfChildren;

    @NotNull
    private double totalFare;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    
}
