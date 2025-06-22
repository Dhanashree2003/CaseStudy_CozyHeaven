package com.hexaware.cozyheaven.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyheaven.hotelbooking.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	//display reviews by hotel id
    List<Review> findByHotel_HotelID(Long hotelID);
    
}
