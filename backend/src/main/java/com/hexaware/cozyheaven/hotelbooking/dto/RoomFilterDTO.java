package com.hexaware.cozyheaven.hotelbooking.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RoomFilterDTO {
	private Long hotelID;
    private Integer minOccupancy;
    private Double maxPrice;
    private String roomSize;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String location;
    private Boolean ac;
}
