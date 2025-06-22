package com.hexaware.cozyheaven.hotelbooking.testservice;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.hexaware.cozyheaven.hotelbooking.service.OwnerServiceImpl;

class OwnerServiceImplTest {

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private RoomRepository roomRepo;

    @Mock
    private PaymentRepository paymentRepo;

    @Mock
    private HotelRepository hotelRepo;

    @Mock
    private ReviewRepository reviewRepo;

    @Mock
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookings() {
        Long ownerId = 1L;
        List<Booking> mockBookings = List.of(new Booking());
        when(bookingRepo.findByRoom_Hotel_Owner_UserID(ownerId)).thenReturn(mockBookings);

        List<BookingDTO> result = ownerService.getAllBookings(ownerId);
        assertNotNull(result);
        verify(bookingRepo).findByRoom_Hotel_Owner_UserID(ownerId);
    }

    @Test
    void testUpdateBookingStatus() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingID(bookingId);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingStatus(com.hexaware.cozyheaven.hotelbooking.entity.enums.BookingStatus.Confirmed);

        when(bookingRepo.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingDTO result = ownerService.updateBookingStatus(bookingId, bookingDTO);

        assertEquals(bookingDTO.getBookingStatus(), result.getBookingStatus());
        verify(bookingRepo).save(any(Booking.class));
    }

    @Test
    void testGetAllRooms() {
        Long ownerId = 1L;
        List<Room> mockRooms = List.of(new Room());
        when(roomRepo.findByHotel_Owner_UserID(ownerId)).thenReturn(mockRooms);

        List<RoomDTO> result = ownerService.getAllRooms(ownerId);

        assertNotNull(result);
        verify(roomRepo).findByHotel_Owner_UserID(ownerId);
    }


    @Test
    void testDeleteRoom_Success() {
        Long roomId = 10L;
        when(roomRepo.existsById(roomId)).thenReturn(true);

        assertDoesNotThrow(() -> ownerService.deleteRoom(roomId));
        verify(roomRepo).deleteById(roomId);
    }

    @Test
    void testDeleteRoom_NotFound() {
        Long roomId = 10L;
        when(roomRepo.existsById(roomId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> ownerService.deleteRoom(roomId));
    }

    @Test
    void testGetAllPayments() {
        Long ownerId = 1L;
        List<Payment> payments = List.of(new Payment());

        when(paymentRepo.findByBooking_Room_Hotel_Owner_UserID(ownerId)).thenReturn(payments);

        List<PaymentDTO> result = ownerService.getAllPayments(ownerId);

        assertNotNull(result);
        verify(paymentRepo).findByBooking_Room_Hotel_Owner_UserID(ownerId);
    }

    @Test
    void testGetHotelsByOwner() {
        Long ownerId = 1L;
        List<Hotel> hotels = List.of(new Hotel());

        when(hotelRepo.findByOwner_UserID(ownerId)).thenReturn(hotels);

        List<HotelDTO> result = ownerService.getHotelsByOwner(ownerId);

        assertNotNull(result);
        verify(hotelRepo).findByOwner_UserID(ownerId);
    }

    @Test
    void testGetReviewsByOwner() {
        Long hotelId = 1L;
        List<Review> reviews = List.of(new Review());

        when(reviewRepo.findByHotel_HotelID(hotelId)).thenReturn(reviews);

        List<ReviewDTO> result = ownerService.getReviewsByHotel(hotelId);

        assertNotNull(result);
        verify(reviewRepo).findByHotel_HotelID(hotelId);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User());

        when(userRepo.findAll()).thenReturn(users);

        List<UserDTO> result = ownerService.getAllUsers();

        assertNotNull(result);
        verify(userRepo).findAll();
    }
}
