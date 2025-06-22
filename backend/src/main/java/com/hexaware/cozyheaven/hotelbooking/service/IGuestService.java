package com.hexaware.cozyheaven.hotelbooking.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hexaware.cozyheaven.hotelbooking.dto.AuthRequest;
import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.PaymentDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.ReviewDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserResponseDTO;

public interface IGuestService {

    List<HotelDTO> searchHotels(String location);
    
    double calculateTotalFare(RoomDTO room, int numAdults, int numChildren, List<Integer> childrenAges);

    BookingDTO bookRoom(BookingDTO bookingDTO);

    List<BookingDTO> viewBookingHistory(Long userId);

    BookingDTO cancelBooking(Long bookingId);
    
    UserResponseDTO getUserByFullNameAndPassword(String fullName, String password);
    

	List<RoomDTO> getRoomsByHotelID(Long hotelID);

	List<ReviewDTO> getReviewsByHotelID(Long hotelID);
	
	Set<String> getAllUniqueLocations();

	Optional<UserDTO> authenticate(AuthRequest request);

	Optional getUserSummaryByEmail(String email);

	ReviewDTO submitReview(ReviewDTO dto);

	PaymentDTO makePayment(PaymentDTO paymentDTO);

}
