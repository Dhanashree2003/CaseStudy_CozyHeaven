package com.hexaware.cozyheaven.hotelbooking.testservice;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.cozyheaven.hotelbooking.dto.BookingDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.RoomDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Booking;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.BookingStatus;
import com.hexaware.cozyheaven.hotelbooking.repository.BookingRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.RoomRepository;
import com.hexaware.cozyheaven.hotelbooking.service.GuestServiceImpl;

class GuestServiceImplTest {

    @InjectMocks
    private GuestServiceImpl guestService;

    @Mock
    private HotelRepository hotelRepo;

    @Mock
    private RoomRepository roomRepo;

    @Mock
    private BookingRepository bookingRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchHotels_Found() {
        String location = "Goa";
        List<Hotel> hotels = List.of(new Hotel());
        when(hotelRepo.findByLocation(location)).thenReturn(hotels);

        List<HotelDTO> result = guestService.searchHotels(location);

        assertNotNull(result);
        verify(hotelRepo).findByLocation(location);
    }

    @Test
    void testSearchHotels_NotFound() {
        String location = "Nowhere";
        when(hotelRepo.findByLocation(location)).thenReturn(Collections.emptyList());

        List<HotelDTO> result = guestService.searchHotels(location);

        assertTrue(result.isEmpty());
    }

    

    @Test
    void testCalculateTotalFare_WithinLimit() {
        RoomDTO room = new RoomDTO();
        room.setBaseFare(1000);
        room.setMaxOccupancy(4);

        double fare = guestService.calculateTotalFare(room, 2, 1, List.of(5));
        assertEquals(1000, fare); // within limit, no extra charge
    }

    @Test
    void testCalculateTotalFare_ExceedsLimit_WithAdult() {
        RoomDTO room = new RoomDTO();
        room.setBaseFare(1000);
        room.setMaxOccupancy(2);

        double fare = guestService.calculateTotalFare(room, 3, 0, List.of());
        // extra 1 adult = 40% extra
        assertEquals(1000 + 1000 * 0.4, fare);
    }

    @Test
    void testCalculateTotalFare_ExceedsLimit_WithChild() {
        RoomDTO room = new RoomDTO();
        room.setBaseFare(1000);
        room.setMaxOccupancy(2);

        double fare = guestService.calculateTotalFare(room, 1, 2, List.of(10, 16));
        // total = 3, allowed = 2, one extra
        // index 2 = child age 16 > 14 => treated as adult => 40%
        assertEquals(1000 + 1000 * 0.4, fare);
    }

    @Test
    void testBookRoom() {
        BookingDTO bookingDTO = new BookingDTO();
        Booking booking = new Booking();
        booking.setBookingID(1L);

        // You can mock static MapperUtil with tools like PowerMockito or if converted to an injectable mapper
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingDTO result = guestService.bookRoom(bookingDTO);

        assertNotNull(result);
        verify(bookingRepo).save(any(Booking.class));
    }

    @Test
    void testViewBookingHistory() {
        Long userId = 1L;
        List<Booking> bookings = List.of(new Booking());

        when(bookingRepo.findBookingsByUserId(userId)).thenReturn(bookings);

        List<BookingDTO> result = guestService.viewBookingHistory(userId);

        assertNotNull(result);
        verify(bookingRepo).findBookingsByUserId(userId);
    }

    @Test
    void testCancelBooking_BookingFound() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingID(bookingId);
        booking.setBookingStatus(BookingStatus.Confirmed);

        when(bookingRepo.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingDTO result = guestService.cancelBooking(bookingId);

        assertEquals(BookingStatus.Cancelled, booking.getBookingStatus());
        verify(bookingRepo).save(booking);
    }

    @Test
    void testCancelBooking_NotFound() {
        Long bookingId = 999L;

        when(bookingRepo.findById(bookingId)).thenReturn(Optional.empty());

        BookingDTO result = guestService.cancelBooking(bookingId);

        assertNull(result);
    }
}
