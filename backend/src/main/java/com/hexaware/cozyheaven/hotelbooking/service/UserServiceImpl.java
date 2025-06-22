package com.hexaware.cozyheaven.hotelbooking.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User updateUser (User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    
}
