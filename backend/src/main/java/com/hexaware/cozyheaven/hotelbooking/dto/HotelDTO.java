package com.hexaware.cozyheaven.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelDTO {
	
	private Long hotelID;
    private String hotelName;
    private String location;
    private String amenities;
    private String imgUrl;
    private Long ownerID;

}
