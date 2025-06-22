package com.hexaware.cozyheaven.hotelbooking.service;


import com.hexaware.cozyheaven.hotelbooking.entity.User;

public interface IUserService {

    User updateUser (User user);
    User getByUserId(Long userId);

}
