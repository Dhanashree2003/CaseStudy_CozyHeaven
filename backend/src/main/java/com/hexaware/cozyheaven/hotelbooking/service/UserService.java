package com.hexaware.cozyheaven.hotelbooking.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.cozyheaven.hotelbooking.dto.UserSummaryDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
  
    public boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
	
	
	public String addUser(User user) {  // user registration
		if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "user added to system ";
    }


	
    public Optional getUserSummaryByEmail(String email) {
        return repository.findByEmail(email)
            .map(user -> new UserSummaryDTO(user.getUserID(), user.getRole()));
    }

}