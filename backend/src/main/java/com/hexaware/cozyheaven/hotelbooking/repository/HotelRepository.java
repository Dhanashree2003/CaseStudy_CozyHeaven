package com.hexaware.cozyheaven.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	//hotels based on location
    List<Hotel> findByLocation(String location);
    
    //ownerID to list hotels
    List<Hotel> findByOwner_UserID(Long ownerId);
    
    //collects distinct locations of hotel as a list
    @Query("SELECT DISTINCT h.location FROM Hotel h")
    List<String> findDistinctLocations();

}
