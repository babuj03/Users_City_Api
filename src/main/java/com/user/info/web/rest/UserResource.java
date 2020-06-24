package com.user.info.web.rest;

import com.user.info.service.UserService;
import com.user.info.service.dto.UserDTO;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private  UserService userService;
    
	 public UserResource(UserService userService) {
		   this.userService = userService;
	  }
    
    /**
     * {@code GET /users} : get all users.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        final List<UserDTO> users = userService.users();
        
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    
    /**
     * Gets users by id.
     * @param userId the user id
     * @return the users by id
     * @throws UserNotFoundException the resource not found exception
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name="id",required = true ) String id) {
    	UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
   
    
    
    /**
     * {@code GET /users} : get all users by city and with in given miles
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/city/{city}/users")
    public ResponseEntity<List<UserDTO>> getUserByCity(@PathVariable(name="city", required = true ) String city, @RequestParam(name="distance", required = false) Float distance, @RequestParam(name="units", required = false, defaultValue = "mile") String units) {
    	 List<UserDTO> users = userService.getUserByCity(city);
    	 //Filter users by distance
    	 if(distance != null && distance > 0.0f) {
    		 users= userService.filterByDistance(users,distance, units);
    	 }
    		 
    	 return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    
    
   
}
