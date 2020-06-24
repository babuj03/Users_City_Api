package com.user.info.service.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.info.service.dto.UserDTO;

@FeignClient(name="${app.name}", url="${bpdts.test.app}")
public interface UserServiceProxy {	

	@GetMapping(value="/users", consumes = "application/json")
	public List<UserDTO> getUsers() ;
	
	@GetMapping( value="/user/{id}", consumes = "application/json")
	public UserDTO getUserById(@PathVariable(name = "id") String id) ;

	@GetMapping(value="/city/{city}/users", consumes = "application/json")
	public List<UserDTO> getUserByCity(@PathVariable(name = "city") String city);

	
}
