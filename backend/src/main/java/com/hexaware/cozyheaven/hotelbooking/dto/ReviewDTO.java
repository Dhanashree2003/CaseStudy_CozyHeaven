package com.hexaware.cozyheaven.hotelbooking.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewDTO {
	private Long reviewID;
    private Long userID;
    private Long hotelID;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
	

}
