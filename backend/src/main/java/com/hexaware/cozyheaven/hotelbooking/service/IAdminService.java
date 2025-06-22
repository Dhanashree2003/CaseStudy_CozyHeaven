package com.hexaware.cozyheaven.hotelbooking.service;

import java.util.List;

import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;

public interface IAdminService {
	
    // User and Owner
	
	
  // UserDTO addUser(UserDTO userDto);
   void deleteUser(Long userID);
   List<UserDTO> getAllUsers();
   List<UserDTO> getAllOwners();
   UserDTO getUserById(Long userID);
   UserDTO getOwnerById(Long ownerID);

    // Hotel
    Hotel addHotel(HotelDTO hotelDto);
    void deleteHotel(Long hotelID);
    List<HotelDTO> getAllHotels();
    HotelDTO getHotelById(Long hotelID);
	Hotel updateHotel(HotelDTO dto);


}
