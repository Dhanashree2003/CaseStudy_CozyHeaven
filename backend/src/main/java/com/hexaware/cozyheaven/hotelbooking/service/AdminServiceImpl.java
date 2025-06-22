package com.hexaware.cozyheaven.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.Role;
import com.hexaware.cozyheaven.hotelbooking.exception.ResourceNotFoundException;
import com.hexaware.cozyheaven.hotelbooking.repository.BookingRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.UserRepository;
import com.hexaware.cozyheaven.hotelbooking.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HotelRepository hotelRepo;
    
    @Autowired
    private BookingRepository bookingRepo;


	/*
	 This Service manages
	 all admin related operations
	 in the hotel booking management
	 */


    @Override
    public void deleteUser(Long userId) {
        if (!userRepo.existsById(userId)) {
        	log.warn("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        log.info("Deleting bookings for user ID: {}", userId);
        bookingRepo.deleteByUserId(userId);
        userRepo.deleteById(userId);
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
    	log.info("Fetching all users with role: Guest");
        List<User> users = userRepo.findByRole(Role.Guest);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(MapperUtil.toUserDTO(user));
        }
        return userDTOs;
    }

    @Override
    public List<UserDTO> getAllOwners() {
    	log.info("Fetching all users with role: Owner");
        List<User> owners = userRepo.findByRole(Role.Owner);
        List<UserDTO> ownerDTOs = new ArrayList<>();
        for (User owner : owners) {
            ownerDTOs.add(MapperUtil.toUserDTO(owner));
        }
        return ownerDTOs;
    }

    @Override
    public UserDTO getUserById(Long userId) {
    	log.info("Fetching Guest user with ID: {}", userId);
        User user = userRepo.findUserByIdAndRole(userId, Role.Guest);
        		if (user == null) {
        		    throw new ResourceNotFoundException("Guest not found with ID: " + userId);
        		}
        return MapperUtil.toUserDTO(user);
    }

    @Override
    public UserDTO getOwnerById(Long ownerId) {
    	log.info("Fetching Owner user with ID: {}", ownerId);
        User user = userRepo.findUserByIdAndRole(ownerId, Role.Owner);
        if (user == null) {
        		    throw new ResourceNotFoundException("Owner not found with ID: " + ownerId);
        }
        return MapperUtil.toUserDTO(user);
    }

    @Override
    public Hotel addHotel(HotelDTO dto) {
    	log.info("Adding new hotel: {}", dto.getHotelName());
        Hotel hotel = new Hotel();
        hotel.setHotelName(dto.getHotelName());
        hotel.setLocation(dto.getLocation());
        hotel.setAmenities(dto.getAmenities());
        hotel.setImgUrl(dto.getImgUrl());

        User owner = userRepo.findById(dto.getOwnerID())
            .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + dto.getOwnerID()));

        hotel.setOwner(owner);

        return hotelRepo.save(hotel);
    }
    
    @Override
    public Hotel updateHotel(HotelDTO dto) {
    	log.info("Updating hotel with ID: {}", dto.getHotelID());
        Hotel hotel = hotelRepo.findById(dto.getHotelID())
            .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + dto.getHotelID()));

        hotel.setHotelName(dto.getHotelName());
        hotel.setLocation(dto.getLocation());
        hotel.setAmenities(dto.getAmenities());
        hotel.setImgUrl(dto.getImgUrl());

        if (dto.getOwnerID() != null) {
            User owner = userRepo.findById(dto.getOwnerID())
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + dto.getOwnerID()));
            hotel.setOwner(owner);
        }

        return hotelRepo.save(hotel);
    }

    
    @Override
    public void deleteHotel(Long hotelId) {
        if (!hotelRepo.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with ID: " + hotelId);
        }
        log.info("Deleting hotel with ID: {}", hotelId);
        hotelRepo.deleteById(hotelId);
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        return MapperUtil.toHotelDTOList(hotelRepo.findAll());
    }

    @Override
    public HotelDTO getHotelById(Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        return MapperUtil.toHotelDTO(hotel);
    }
}
