package com.hexaware.cozyheaven.hotelbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
	
	//lists users by role
	List<User> findByRole(Role role);
	@Query("SELECT u FROM User u WHERE u.userID = :userID AND u.role = :role")
	User findUserByIdAndRole(@Param("userID") Long userID, @Param("role") Role role);
	
	Optional<User> findByFullNameAndPassword(String fullName, String password);
	
	//find user by email
	Optional<User> findByEmail(String email);
}
