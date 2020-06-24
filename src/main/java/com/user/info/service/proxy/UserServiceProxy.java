package com.user.info.service.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.info.service.dto.UserDTO;

//https://bpdts-test-app.herokuapp.com/users
@FeignClient(name="users", url = "bpdts-test-app.herokuapp.com")
public interface UserServiceProxy {	

	@GetMapping("/users")
	public List<UserDTO> getUsers() ;
	
	@GetMapping("/user/{id}")
	public UserDTO getUserById(@PathVariable(name = "id") String id) ;

	@GetMapping("/city/{city}/users")
	public List<UserDTO> getUserByCity(@PathVariable(name = "city") String city);

	

}
