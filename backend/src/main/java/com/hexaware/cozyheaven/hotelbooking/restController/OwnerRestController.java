package com.hexaware.cozyheaven.hotelbooking.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.PaymentDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.ReviewDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.Room;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.service.IOwnerService;

import jakarta.validation.Valid;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/owner")
public class OwnerRestController {

    @Autowired
    private IOwnerService ownerService;
    private final HotelRepository hotelRepo;
    
    /*
	 This controller manages
	 all owner related operations
	 in the hotel booking management
	 */
    
    // get all bookings
    @GetMapping("/bookings")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<BookingDTO> getAllBookings(@RequestParam Long ownerId) {
        return ownerService.getAllBookings(ownerId);
    }

    //updates the bookings status
    @PutMapping("/bookings/{bookingId}/status")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public BookingDTO updateBookingStatus(@PathVariable Long bookingId,@Valid @RequestBody BookingDTO bookingDTO) {
    	log.info("Booking status updated");
        return ownerService.updateBookingStatus(bookingId, bookingDTO);
    }
    
    
    // get all rooms according to the ownerID
    @GetMapping("rooms/{hotelID}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<RoomDTO> getAllRooms(@PathVariable Long hotelID) {
        return ownerService.getAllRooms(hotelID);
    }
    
    //add rooms according to the hotel id
    @PostMapping("/hotels/{hotelId}/rooms")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<?> addRoomToHotel(@PathVariable Long hotelId,@Valid @RequestBody RoomDTO roomDTO) {
        log.info("Received request to add room to hotel ID: {}", hotelId);

        Optional<Hotel> hotelOpt = hotelRepo.findById(hotelId);
        if (hotelOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Hotel not found with ID: " + hotelId);
        }

        Hotel hotel = hotelOpt.get();

        // Convert RoomDTO to Room entity manually
        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomName(roomDTO.getRoomName());
        room.setRoomSize(roomDTO.getRoomSize());
        room.setBedType(roomDTO.getBedType());
        room.setMaxOccupancy(roomDTO.getMaxOccupancy());
        room.setBaseFare(roomDTO.getBaseFare());
        room.setAc(roomDTO.isAc());
        room.setRoomStatus(roomDTO.getRoomStatus());

        Room savedRoom = ownerService.saveRoom(room);

        return ResponseEntity.ok(savedRoom);
    }
    
    //update the rooms by room id
    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId,@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO updated = ownerService.updateRoom(roomId, roomDTO);
        return ResponseEntity.ok(updated);
    }
    
    //delete room by passing roomID
    @DeleteMapping("/rooms/{roomId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public void deleteRoom(@PathVariable Long roomId) {
    	log.info("Room deleted successfully");
        ownerService.deleteRoom(roomId);
    }
    
    //get the all payments
    @GetMapping("/payments")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<PaymentDTO> getAllPayments(@RequestParam Long ownerId) {
        return ownerService.getAllPayments(ownerId);
    }

    //get hotels by owner id
    @GetMapping("/hotels")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<HotelDTO> getHotelsByOwner(@RequestParam Long ownerId) {
        return ownerService.getHotelsByOwner(ownerId);
    }
   
    //get reviews by hotel id
    @GetMapping("/reviews")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<ReviewDTO> getReviewsByOwner(@RequestParam Long hotelId) {
        return ownerService.getReviewsByHotel(hotelId);
    }
    
    //get all guests of the corresponding hotel
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public List<UserDTO> getAllUsers() {
        return ownerService.getAllUsers();
    }
}