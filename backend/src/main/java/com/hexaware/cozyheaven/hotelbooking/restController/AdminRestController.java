package com.hexaware.cozyheaven.hotelbooking.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.service.IAdminService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private IAdminService adminService;
	/*
	 This controller manages
	 all admin related operations
	 in the hotel booking management
	 */

    //manage guest

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }
    
    
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }
    
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }
    
    // manage owner 
    
    @GetMapping("/owners")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO> getAllOwners() {
        return adminService.getAllOwners();
    }

    @GetMapping("/owners/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO getOwnerById(@PathVariable Long id) {
        return adminService.getOwnerById(id);
    }
    
    @DeleteMapping("/hotels/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteHotel(@PathVariable Long id) {
        adminService.deleteHotel(id);
    }

    // Hotel Management 

    @PostMapping("/hotels")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Hotel> addHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        if (hotelDTO.getOwnerID() == null) {
            throw new IllegalArgumentException("ownerID must not be null");
        }
        Hotel addedHotel = adminService.addHotel(hotelDTO);
        log.info("Hotel added successfully");
        return ResponseEntity.ok(addedHotel);
    }

    @PutMapping("/hotels/{hotelId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long hotelId,@Valid @RequestBody HotelDTO hotelDTO) {
        if (!hotelId.equals(hotelDTO.getHotelID())) {
            return ResponseEntity.badRequest().build(); // Hotel ID mismatch
        }

        Hotel updated = adminService.updateHotel(hotelDTO);
        return ResponseEntity.ok(updated);
    }
    

    @GetMapping("/hotels")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<HotelDTO> getAllHotels() {
        return adminService.getAllHotels();
    }
    
    @GetMapping("/hotels/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public HotelDTO getHotelById(@PathVariable Long id) {
        return adminService.getHotelById(id);
    }
}