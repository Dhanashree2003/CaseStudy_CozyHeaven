package com.hexaware.cozyheaven.hotelbooking.testservice;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.cozyheaven.hotelbooking.dto.HotelDTO;
import com.hexaware.cozyheaven.hotelbooking.dto.UserDTO;
import com.hexaware.cozyheaven.hotelbooking.entity.Hotel;
import com.hexaware.cozyheaven.hotelbooking.entity.User;
import com.hexaware.cozyheaven.hotelbooking.entity.enums.Role;
import com.hexaware.cozyheaven.hotelbooking.exception.ResourceNotFoundException;
import com.hexaware.cozyheaven.hotelbooking.repository.HotelRepository;
import com.hexaware.cozyheaven.hotelbooking.repository.UserRepository;
import com.hexaware.cozyheaven.hotelbooking.service.AdminServiceImpl;

@SpringBootTest
class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private HotelRepository hotelRepo;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setUserID(1L);
        testUser.setRole(Role.Guest);
        testUser.setFullName("John");
    }



    @Test
    void testDeleteUser_UserExists() {
        Long userId = 1L;
        when(userRepo.existsById(userId)).thenReturn(true);
        adminService.deleteUser(userId);
        verify(userRepo, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 99L;
        when(userRepo.existsById(userId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteUser(userId));
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepo.findUserByIdAndRole(1L, Role.Guest)).thenReturn(testUser);
        UserDTO dto = adminService.getUserById(1L);
        assertNotNull(dto);
        assertEquals("John", dto.getFullName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepo.findUserByIdAndRole(1L, Role.Guest)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> adminService.getUserById(1L));
    }

    @Test
    void testAddHotel_Success() {
        HotelDTO dto = new HotelDTO();
        dto.setHotelName("The Grand");
        dto.setLocation("NYC");
        dto.setAmenities("Pool,WiFi");
        dto.setOwnerID(1L);

        Hotel hotel = new Hotel();
        hotel.setHotelName("The Grand");
        hotel.setLocation("NYC");

        when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel);

        Hotel savedHotel = adminService.addHotel(dto);
        assertEquals("The Grand", savedHotel.getHotelName());
    }

    @Test
    void testAddHotel_OwnerNotFound() {
        HotelDTO dto = new HotelDTO();
        dto.setOwnerID(2L);

        when(userRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> adminService.addHotel(dto));
    }
}
