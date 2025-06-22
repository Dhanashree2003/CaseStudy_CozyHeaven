package com.hexaware.cozyheaven.hotelbooking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.cozyheaven.hotelbooking.entity.User;
@Repository
public interface UserInfoRepository extends JpaRepository<User, Long> {
 
	//find user by their email 
   @Query("select u from User u where u.email = :email")
   User findUserByEmail(@Param("email") String email);



}