package com.hexaware.cozyheaven.hotelbooking.restController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cozyheaven.hotelbooking.dto.AuthRequest;
import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelFilterDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.PaymentDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.ReviewDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomFilterDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserResponseDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserSummaryDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.Room;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.RoomRepository;
import com.hexaware.cozyheaven.hotelbooking.service.IGuestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/guest")
public class GuestRestController {

	@Autowired
	private IGuestService guestService;
	private final HotelRepository hotelRepo;
	private final RoomRepository roomRepository;
	

	/*
	 This controller manages
	 all guest related operations
	 in the hotel booking management
	 */

	//fetches hotel based on the location
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/hotels")
	public List<HotelDTO> searchHotels(@RequestParam String location) {
		return guestService.searchHotels(location);
	}
	
	//booking rooms
	@PostMapping("/booking")
	@PreAuthorize("hasRole('GUEST')")
	public BookingDTO bookRoom(@Valid @RequestBody BookingDTO bookingDTO) {
		log.info("Rooms booked successfully");
		return guestService.bookRoom(bookingDTO);
	}
	
	//get the booking history
	@GetMapping("/bookings/{guestId}")
	@PreAuthorize("hasRole('GUEST')")
	public List<BookingDTO> getBookingHistory(@PathVariable Long guestId) {
	    return guestService.viewBookingHistory(guestId);
	}

	
	//cancel the booking
	@PutMapping("/booking/cancel/{bookingId}")
	@PreAuthorize("hasRole('GUEST')")
	public BookingDTO cancelBooking(@PathVariable Long bookingId) {
		log.info("Booking Cancelled");
		return guestService.cancelBooking(bookingId);
	}
	
	@PreAuthorize("hasRole('GUEST')")
	@GetMapping("/user")
	public UserResponseDTO getUser(@RequestParam String fullName, @RequestParam String password) {
	        return guestService.getUserByFullNameAndPassword(fullName, password);
	 }
	
	 @GetMapping("/hotels/{hotelID}/rooms")
	 @PreAuthorize("hasRole('GUEST')")
	    public List<RoomDTO> getRoomsByHotelID(@PathVariable Long hotelID) {
	        return guestService.getRoomsByHotelID(hotelID);
	    }
	 
	 @GetMapping("/review/{hotelId}")
	 @PreAuthorize("hasRole('GUEST')")
	    public ResponseEntity<List<ReviewDTO>> getReviewsByHotelId(@PathVariable Long hotelId) {
	        List<ReviewDTO> reviews = guestService.getReviewsByHotelID(hotelId);
	        return ResponseEntity.ok(reviews);
	    }
	 
	 @GetMapping("/locations")
	 @PreAuthorize("hasRole('GUEST')")
	    public ResponseEntity<Set<String>> getAllHotelLocations() {
	        Set<String> locations = guestService.getAllUniqueLocations();
	        return ResponseEntity.ok(locations);
	    }
	 
	 
	 // getting guestid part
	 
	 @PostMapping("/login")
	 @PreAuthorize("hasRole('GUEST')")
	 public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	     Optional<UserDTO> user = guestService.authenticate(request);

	     if (user.isPresent()) {
	         return ResponseEntity.ok(user.get());
	     } else {
	         System.out.println("Invalid login attempt: " + request.getEmail());
	         return ResponseEntity.status(401).body("Invalid credentials");
	     }
	 }
	 
	 @GetMapping("/userId")
	 public ResponseEntity<?> getUserSummary(@RequestParam String fullName) {
	     Optional<UserSummaryDTO> summaryOpt = guestService.getUserSummaryByEmail(fullName);

	     if (summaryOpt.isPresent()) {
	         return ResponseEntity.ok(summaryOpt.get());
	     } else {
	         return ResponseEntity.status(404).body("User not found");
	     }
	 }
	 
	 @PostMapping("/addReview")
	 @PreAuthorize("hasRole('GUEST')")
	 public ResponseEntity<ReviewDTO> submitReview(@RequestBody ReviewDTO reviewDTO) {
	        return ResponseEntity.ok(guestService.submitReview(reviewDTO));
	    }
	 
	 @PostMapping("/pay")
	 @PreAuthorize("hasRole('GUEST')")
	    public ResponseEntity<PaymentDTO> makePayment(@RequestBody PaymentDTO paymentDTO) {
	        PaymentDTO savedPayment = guestService.makePayment(paymentDTO);
	        return ResponseEntity.ok(savedPayment);
	    }

		/*
		 * Filter api to search rooms 
		 * and hotels according to 
		 * guest choices in search area
		 */
	 
	 @PostMapping("/filter")
	 @PreAuthorize("hasRole('GUEST')")
	 public ResponseEntity<?> filterHotels(@RequestBody HotelFilterDTO filterDTO) {

	     // Only validate if both dates are provided
	     if (filterDTO.getCheckInDate() != null || filterDTO.getCheckOutDate() != null) {

	         if (!filterDTO.isCheckInNotPast()) {
	             return ResponseEntity.badRequest().body("Check-in date cannot be in the past");
	         }

	         if (!filterDTO.isCheckOutNotPast()) {
	             return ResponseEntity.badRequest().body("Check-out date cannot be in the past");
	         }

	         if (!filterDTO.isValidDateRange()) {
	             return ResponseEntity.badRequest().body("Check-out date must be after check-in date");
	         }
	     }

	     List<Hotel> hotels = roomRepository.filterHotelsWithAvailability(
	             filterDTO.getLocation(),
	             filterDTO.getAc(),
	             filterDTO.getCheckInDate(),
	             filterDTO.getCheckOutDate()
	     );

	     return ResponseEntity.ok(hotels);
	 }
	 
	 @PostMapping("/roomFilter")
	 public ResponseEntity<?> filterRooms(@RequestBody RoomFilterDTO searchDTO) {

	     // Optional date validations
	     if (searchDTO.getCheckInDate() != null || searchDTO.getCheckOutDate() != null) {
	         if (searchDTO.getCheckInDate() != null && searchDTO.getCheckInDate().isBefore(LocalDate.now())) {
	             return ResponseEntity.badRequest().body("Check-in date cannot be in the past");
	         }

	         if (searchDTO.getCheckOutDate() != null && searchDTO.getCheckOutDate().isBefore(LocalDate.now())) {
	             return ResponseEntity.badRequest().body("Check-out date cannot be in the past");
	         }

	         if (searchDTO.getCheckInDate() != null && searchDTO.getCheckOutDate() != null &&
	             searchDTO.getCheckOutDate().isBefore(searchDTO.getCheckInDate())) {
	             return ResponseEntity.badRequest().body("Check-out date must be after check-in date");
	         }
	     }

	     List<Room> rooms = roomRepository.filterRooms(
	    		 searchDTO.getHotelID(),
	             searchDTO.getMinOccupancy(),
	             searchDTO.getMaxPrice(),
	             searchDTO.getRoomSize(),
	             searchDTO.getCheckInDate(),
	             searchDTO.getCheckOutDate(),
	             searchDTO.getLocation(),
	             searchDTO.getAc()
	     );

	     return ResponseEntity.ok(rooms);
	 }



}
