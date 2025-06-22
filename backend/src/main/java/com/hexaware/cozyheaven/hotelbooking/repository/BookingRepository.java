package com.hexaware.cozyheaven.hotelbooking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.cozyheaven.hotelbooking.entity.Booking;

import jakarta.transaction.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.user.userID = :userId")
	List<Booking> findBookingsByUserId(@Param("userId") Long userId);
	
	List<Booking> findByRoom_Hotel_Owner_UserID(Long ownerId);
    // This list bookings for rooms where the room's hotel's owner matches ownerId

	@Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.user.userID = :userId")
    void deleteByUserId(@Param("userId") Long userId);
	
	@Query("SELECT b.room.roomID FROM Booking b " +
	"WHERE b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate")
	List<Long> findBookedRoomIdsBetweenDates(LocalDate checkInDate, LocalDate checkOutDate);
}
