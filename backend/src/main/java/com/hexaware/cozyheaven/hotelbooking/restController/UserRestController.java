package com.hexaware.cozyheaven.hotelbooking.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.service.IUserService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAnyRole('GUEST','ADMIN','OWNER')")
    @GetMapping("/getbyid/{userId}")
    public User getById(@PathVariable Long userId) {
        return userService.getByUserId(userId);
    }

    @PreAuthorize("hasAnyRole('GUEST','ADMIN','OWNER')")
    @PutMapping("/update")
    public User updateUser (@RequestBody User user) {
        return userService.updateUser (user);
    }

    
    

}

