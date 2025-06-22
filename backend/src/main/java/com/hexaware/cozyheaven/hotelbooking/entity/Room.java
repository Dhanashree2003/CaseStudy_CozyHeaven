
package com.hexaware.cozyheaven.hotelbooking.entity;

import com.hexaware.cozyheaven.hotelbooking.entity.enums.BedType;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.Status;

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
import lombok.ToString;

@Entity
@Table(name = "rooms")
@ToString(exclude = "hotel")

@Data
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomID;

	@ManyToOne
	@JoinColumn(name = "HotelID")
	private Hotel hotel; // Mapping to Hotel entity
	
	@NotNull
	private String roomName;
	
	@NotNull
	private String roomSize;

	@Enumerated(EnumType.STRING)
	private BedType bedType;

	@NotNull
	@Min(value = 1, message = "Occupancy must be at least 1")
	private int maxOccupancy;

	@NotNull
	private double baseFare;

	@NotNull
	private boolean ac;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Status roomStatus;

}