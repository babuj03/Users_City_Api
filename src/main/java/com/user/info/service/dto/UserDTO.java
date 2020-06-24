package com.user.info.service.dto;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO representing a user, with his authorities.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
    private Long id;
    
    private String first_name;
    
    private String last_name;
    
    private String email;
    
    private String ip_address;
    
    private double latitude;
    
    private double longitude;
    
    private String createdBy;

    private LocalDate createdDate = LocalDate.now();

   }
