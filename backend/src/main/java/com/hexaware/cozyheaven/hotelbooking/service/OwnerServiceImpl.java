package com.hexaware.cozyheaven.hotelbooking.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.PaymentDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.ReviewDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Booking;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.Payment;
import com.hexaware.cozyheaven.hotelbooking.entity.Review;
import com.hexaware.cozyheaven.hotelbooking.entity.Room;
import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.repository.BookingRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.PaymentRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.ReviewRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.RoomRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.UserRepository;
import com.hexaware.cozyheaven.hotelbooking.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OwnerServiceImpl implements IOwnerService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;
    
    /*
	 This Service manages
	 all guest related operations
	 in the hotel booking management
	 */

    @Override
    public List<BookingDTO> getAllBookings(Long ownerId) {
    	log.info("Fetching all bookings for owner ID: {}", ownerId);
        List<Booking> bookings = bookingRepo.findByRoom_Hotel_Owner_UserID(ownerId);
        return MapperUtil.toBookingDTOList(bookings);
    }

    @Override
    public BookingDTO updateBookingStatus(Long bookingId, BookingDTO bookingDTO) {
    	log.info("Updating booking status for booking ID: {}", bookingId);
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        bookingRepo.save(booking);
        return MapperUtil.toBookingDTO(booking);
    }

    @Override
    public List<RoomDTO> getAllRooms(Long hotelID) {
    	log.info("Fetching all rooms for hotel ID: {}", hotelID);
        List<Room> rooms = roomRepo.findByHotel_HotelID(hotelID);
        return MapperUtil.toRoomDTOList(rooms);
    }


    @Override
    public Room saveRoom(Room room) {
    	log.info("Saving new room with name: {}", room.getRoomName());
        return roomRepo.save(room);
    }


    
    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO updatedRoomDTO) {
    	log.info("Updating room with ID: {}", roomId);
        Optional<Room> optionalRoom = roomRepo.findById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("Room not found with ID: " + roomId);
        }

        Room room = optionalRoom.get();

        // Set new values
        room.setRoomName(updatedRoomDTO.getRoomName());
        room.setRoomSize(updatedRoomDTO.getRoomSize());
        room.setBedType(updatedRoomDTO.getBedType());
        room.setMaxOccupancy(updatedRoomDTO.getMaxOccupancy());
        room.setBaseFare(updatedRoomDTO.getBaseFare());
        room.setAc(updatedRoomDTO.isAc());
        room.setRoomStatus(updatedRoomDTO.getRoomStatus());

        // Optionally update hotel if needed
        if (updatedRoomDTO.getHotelID() != null) {
            Hotel hotel = hotelRepo.findById(updatedRoomDTO.getHotelID())
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
            room.setHotel(hotel);
        }

        Room saved = roomRepo.save(room);
        log.info("Room updated successfully for ID: {}", roomId);
        return MapperUtil.toRoomDTO(saved);
    }

    @Override
    public void deleteRoom(Long roomId) {
        if (!roomRepo.existsById(roomId)) {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
        roomRepo.deleteById(roomId);
        log.info("Room deleted successfully with ID: {}", roomId);
    }

    @Override
    public List<PaymentDTO> getAllPayments(Long ownerId) {
    	log.info("Fetching all payments for owner ID: {}", ownerId);
        List<Payment> payments = paymentRepo.findByBooking_Room_Hotel_Owner_UserID(ownerId);
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOs.add(MapperUtil.toPaymentDTO(payment));
        }
        return paymentDTOs;
    }

    @Override
    public List<HotelDTO> getHotelsByOwner(Long ownerId) {
    	log.info("Fetching hotels for owner ID: {}", ownerId);
        List<Hotel> hotels = hotelRepo.findByOwner_UserID(ownerId);
        List<HotelDTO> hotelDTOs = new ArrayList<>();
        for (Hotel hotel : hotels) {
            hotelDTOs.add(MapperUtil.toHotelDTO(hotel));
        }
        return hotelDTOs;
    }

    @Override
    public List<ReviewDTO> getReviewsByHotel(Long hotelId) {
    	log.info("Fetching reviews for hotel ID: {}", hotelId);
        List<Review> reviews = reviewRepo.findByHotel_HotelID(hotelId);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(MapperUtil.toReviewDTO(review));
        }
        return reviewDTOs;
    }

    @Override
    public List<UserDTO> getAllUsers() {
    	log.info("Fetching all users");
        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(MapperUtil.toUserDTO(user));
        }
        return userDTOs;
    }

}
