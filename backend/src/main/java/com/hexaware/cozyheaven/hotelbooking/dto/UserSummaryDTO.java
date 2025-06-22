package com.hexaware.cozyheaven.hotelbooking.dto;


import com.hexaware.cozyheaven.hotelbooking.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDTO {
    private Long userID;
    private Role role;
}