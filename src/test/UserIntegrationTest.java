package com.users.info.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.user.info.UsersApp;
import com.user.info.service.dto.UserDTO;
import com.user.info.web.rest.errors.ExceptionResponse;

@SpringBootTest(classes = UsersApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

	@LocalServerPort
	int port;
	
	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;
	 
	static TestRestTemplate testRestTemplate = null;

	@BeforeAll
	public static void init() {
		testRestTemplate = new TestRestTemplate();
	}
	
	@Test
	public void shoudReturn_401_status() throws Exception {
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(createURLWithPort("/users/"), String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
		
	}
	
	@Test
	public void getAllUser() {
		ResponseEntity<UserDTO[]> responseEntity = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/users/"), UserDTO[].class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertTrue(responseEntity.getBody().length > 0);
	}
	
	@Test
	public void getUserById() {
		ResponseEntity<UserDTO> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/users/1/") , UserDTO.class);
		assertEquals(user.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getUserByCity() {
		ResponseEntity<UserDTO[]> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/city/London/users"), UserDTO[].class);
		assertEquals(user.getStatusCode(), HttpStatus.OK);
		assertTrue(user.getBody().length>0?true:false);
	}
	
	@Test
	public void UserByWrongCity() {
		ResponseEntity<ExceptionResponse> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/city/L234/users"), ExceptionResponse.class);
		assertEquals(user.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY,"Invalid city code");
		
	}
	
	@Test
	public void getUsersInCityWithIn_50_Miles() {
		ResponseEntity<UserDTO[]> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/city/London/users?miles=50"), UserDTO[].class);
		assertEquals(user.getStatusCode(), HttpStatus.OK);
		assertTrue(user.getBody().length>0?true:false,"There is no user found with in 50 miles, try with increased miles");
	}
	
	@Test
	public void getUsersInLondonCityWithIn_25000_Miles() {
		ResponseEntity<UserDTO[]> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/city/London/users?miles=25000"), UserDTO[].class);
		assertEquals(user.getStatusCode(), HttpStatus.OK);
		assertTrue(user.getBody().length>0?true:false,"There is no user found with in 25000 miles, try with increased miles");
	}
	
	@Test
	public void getUserByInvalidID() {
		ResponseEntity<UserDTO> user = testRestTemplate.withBasicAuth(username, password).getForEntity(createURLWithPort("/users/a11111111"), UserDTO.class);
		assertEquals(user.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	
	private String createURLWithPort(String uri) {
	    return "http://localhost:" + port +"/api/v1"+uri;
	}
	
	@AfterAll
	public static void clean() {
		testRestTemplate = null;
	}
}