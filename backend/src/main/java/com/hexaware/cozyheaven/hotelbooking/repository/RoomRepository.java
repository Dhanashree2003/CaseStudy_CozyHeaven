package com.hexaware.cozyheaven.hotelbooking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	List<Room> findByHotel_Owner_UserID(Long ownerId);
	
	List<Room> findByHotel_HotelID(Long hotelID);
	
	//Filter query for hotel search
	@Query("SELECT DISTINCT r.hotel FROM Room r " +
		       "WHERE (:location IS NULL OR :location = '' OR LOWER(r.hotel.location) = LOWER(:location)) " +
		       "AND (:ac IS NULL OR r.ac = :ac) " +
		       "AND r.roomStatus = 'AVAILABLE' " +
		       "AND (" +
		       "  :checkIn IS NULL OR :checkOut IS NULL OR " +
		       "  NOT EXISTS (" +
		       "     SELECT b FROM Booking b " +
		       "     WHERE b.room = r " +
		       "       AND b.bookingStatus = 'CONFIRMED' " +
		       "       AND b.checkInDate < :checkOut AND b.checkOutDate > :checkIn" +
		       "  )" +
		       ")")
		List<Hotel> filterHotelsWithAvailability(@Param("location") String location,
		                                         @Param("ac") Boolean ac,
		                                         @Param("checkIn") LocalDate checkIn,
		                                         @Param("checkOut") LocalDate checkOut);
	

	//Filter search query for room
	@Query("SELECT r FROM Room r " +
		       "JOIN r.hotel h " + 
		       "WHERE (:hotelId IS NULL OR h.hotelID = :hotelId) " +
		       "AND (:minOccupancy IS NULL OR r.maxOccupancy >= :minOccupancy) " +
		       "AND (:maxPrice IS NULL OR r.baseFare <= :maxPrice) " +
		       "AND (:roomSize IS NULL OR :roomSize = '' OR LOWER(r.roomSize) = LOWER(:roomSize)) " +
		       "AND (:ac IS NULL OR r.ac = :ac) " +
		       "AND (:location IS NULL OR LOWER(h.location) = LOWER(:location)) " +
		       "AND r.roomStatus = 'AVAILABLE' " +
		       "AND (" +
		       "  :checkIn IS NULL OR :checkOut IS NULL OR " +
		       "  NOT EXISTS (" +
		       "     SELECT b FROM Booking b " +
		       "     WHERE b.room = r " +
		       "       AND b.bookingStatus = 'CONFIRMED' " +
		       "       AND b.checkInDate < :checkOut AND b.checkOutDate > :checkIn" +
		       "  )" +
		       ")")
		List<Room> filterRooms(@Param("hotelId") Long hotelId,
		                       @Param("minOccupancy") Integer minOccupancy,
		                       @Param("maxPrice") Double maxPrice,
		                       @Param("roomSize") String roomSize,
		                       @Param("checkIn") LocalDate checkIn,
		                       @Param("checkOut") LocalDate checkOut,
		                       @Param("location") String location,
		                       @Param("ac") Boolean ac);





}
