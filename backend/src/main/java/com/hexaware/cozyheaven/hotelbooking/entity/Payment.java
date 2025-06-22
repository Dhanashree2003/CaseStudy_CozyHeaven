package com.hexaware.cozyheaven.hotelbooking.entity;


import java.time.LocalDate;

import com.hexaware.cozyheaven.hotelbooking.entity.enums.PaymentMethod;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking; // Mapping to Booking entity

    @NotNull
    private LocalDate paymentDate;
    
    @NotNull
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    private String transactionID;    
    private String mobileNumber;    
    private String bankName; 
    
    

}
