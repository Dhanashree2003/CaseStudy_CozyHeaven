package com.hexaware.cozyheaven.hotelbooking.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "hotels")
@ToString(exclude = "rooms")

@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelID;

    @NotBlank
    private String hotelName;

    @NotBlank
    private String location;

   
    private String amenities;
    
    @Lob
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "OwnerID")
    private User owner; // Mapping to User entity

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Room> room; // One hotel can have multiple brooms

	
 
}
