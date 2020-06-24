package com.users.info.test.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import com.user.info.UsersApp;
import com.user.info.service.UserService;
import com.user.info.service.dto.UserDTO;
import com.user.info.web.rest.errors.InvalidCityException;
import com.user.info.web.rest.errors.UserNotFoundException;

@SpringBootTest(classes = UsersApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableConfigurationProperties
public class UserTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	UserService userService;

	@LocalServerPort
	int port;

	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;

	static List<UserDTO> dtoList = new ArrayList<UserDTO>();

	String auth;

	@BeforeAll
	public static void init() {
		dtoList.add(UserDTO.builder().first_name("Babu").email("bab@tes.com").id(1l).build());
		dtoList.add(UserDTO.builder().first_name("Jay").email("jay@tes.com").id(2l).build());
	}

	@Test
	public void getAllUser() throws Exception {

		mockmvc.perform(get(createURLWithPort("/users/")).header(HttpHeaders.AUTHORIZATION, getAuthHeader())
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));
		verify(userService, times(1)).users();
	}
	
	@Test
	public void shoudReturn_401_status() throws Exception {
		mockmvc.perform(get(createURLWithPort("/users/")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void getUserById() throws Exception {

		when(userService.getUserById("1")).thenReturn(dtoList.get(0));
		mockmvc.perform(get(createURLWithPort("/users/1")).header(HttpHeaders.AUTHORIZATION, getAuthHeader())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.first_name", is("Babu")));
	}

	@Test
	public void UserNotFound() throws Exception {
		when(userService.getUserById("111111111231231231")).thenThrow(UserNotFoundException.class);
		mockmvc.perform(get(createURLWithPort("/users/111111111231231231"))
				.header(HttpHeaders.AUTHORIZATION, getAuthHeader())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	public void getUserByCity() throws Exception {
		when(userService.getUserByCity("London")).thenReturn(dtoList);
		mockmvc.perform(get(createURLWithPort("/city/London/users")).header(HttpHeaders.AUTHORIZATION, getAuthHeader())
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void wrongCity() throws Exception {
		when(userService.getUserByCity("Lon123")).thenThrow(InvalidCityException.class);
		mockmvc.perform(get(createURLWithPort("/city/Lon123/users")).header(HttpHeaders.AUTHORIZATION, getAuthHeader())
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

	}

	@Test
	public void getUsersInCityWithIn_50_Mile_Default_units() throws Exception {
		when(userService.getUserByCity("London")).thenReturn(dtoList);
		mockmvc.perform(get(createURLWithPort("/city/London/users?distance=50"))
				.header(HttpHeaders.AUTHORIZATION, getAuthHeader()).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	
	@Test
	public void getUsersInCityWithIn_100_KM_Units() throws Exception {
		when(userService.getUserByCity("London")).thenReturn(dtoList);
		mockmvc.perform(get(createURLWithPort("/city/London/users?distance=100&units=Km"))
				.header(HttpHeaders.AUTHORIZATION, getAuthHeader()).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/api/v1" + uri;
	}

	private String getAuthHeader() {
		return "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes());
	}

}
