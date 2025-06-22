package com.hexaware.cozyheaven.hotelbooking.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "User ID")
    private User user; // Mapping to User entity

    @ManyToOne
    @JoinColumn(name = "HotelID")
    private Hotel hotel; // Mapping to Hotel entity

    private int rating;

    private String comment;

 
    private LocalDate reviewDate;

   
}
