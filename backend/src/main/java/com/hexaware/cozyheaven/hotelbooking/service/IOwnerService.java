package com.hexaware.cozyheaven.hotelbooking.service;

import java.util.List;

import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.PaymentDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.ReviewDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Room;

public interface IOwnerService {
	
	
	// Booking management
    List<BookingDTO> getAllBookings(Long ownerId);

    BookingDTO updateBookingStatus(Long bookingId, BookingDTO bookingDTO);

    // Room management
    List<RoomDTO> getAllRooms(Long hotelID);


    //RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);

    void deleteRoom(Long roomId);

    // Payment viewing
    List<PaymentDTO> getAllPayments(Long ownerId);

    // Hotel viewing
    List<HotelDTO> getHotelsByOwner(Long ownerId);

    // Review viewing
    List<ReviewDTO> getReviewsByHotel(Long hotelId);

    // User listing (optional)
    List<UserDTO> getAllUsers();

	Room saveRoom(Room room);

	RoomDTO updateRoom(Long roomId, RoomDTO updatedRoomDTO);

}
