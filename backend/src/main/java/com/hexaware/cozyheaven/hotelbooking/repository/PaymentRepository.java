package com.hexaware.cozyheaven.hotelbooking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyheaven.hotelbooking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	//List payment done to owner by id
	List<Payment> findByBooking_Room_Hotel_Owner_UserID(Long ownerId);
	
	boolean existsByTransactionID(String transactionID);

}
