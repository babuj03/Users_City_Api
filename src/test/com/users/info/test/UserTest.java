package com.users.info.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.info.UsersApp;
import com.user.info.service.UserService;
import com.user.info.service.dto.UserDTO;
import com.user.info.util.ServiceUtil;
import com.user.info.web.rest.errors.InvalidCityException;

import feign.FeignException;

@SpringBootTest(classes = UsersApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableConfigurationProperties
public class UserTest {

	@Value("${city.latitude}")
	private double latitude;
	
	@Value("${city.longitude}")
	private double longitude;

	
	@Autowired
	UserService userService;

	private final Double DISTANCE_LIMIT =50d;
	

    private static List<UserDTO> dtoList ;

    private String cityName = "London";
	
	@BeforeAll
	public static void init() {
		dtoList = new ArrayList<UserDTO>();
		dtoList.add(UserDTO.builder().first_name("Babu").last_name("").email("bab@tes.com").id(1l).latitude(51.507351).longitude(0.118092).build());
		dtoList.add(UserDTO.builder().first_name("Jay").last_name("").email("jay@tes.com").id(2l).latitude(51.5033).longitude(0.1195).build());
		dtoList.add(UserDTO.builder().first_name("Ganesh").last_name("").email("ganesh@tes.com").id(3l).latitude(53.507351).longitude(1.5491).build());
		dtoList.add(UserDTO.builder().first_name("Tej").last_name("").email("Tej@tes.com").id(4l).latitude(54.9783).longitude(1.6178).build());
		
	}
	
	@Test
	public void city_distance_lessThan_50() {
		double distance = ServiceUtil.distance(latitude, longitude, 51.507351d, 0.118092d, "KM");
		assertThat("citydistanceLessThan_50", distance, lessThan(DISTANCE_LIMIT));
	}
	
	@Test
	public void city_distance_greaterThan_50() {
	 double distance =	ServiceUtil.distance(latitude, longitude, 53.8008d, 1.5491d, "KM");
	 assertThat("citydistanceGreaterThan_50",
			 distance,          
	           greaterThan(DISTANCE_LIMIT));
	}  
	
	@Test
	public  void valid_city_name() {
	  assertEquals(true,ServiceUtil.isStringOnlyAlphabet("LONDON"));
	}
	
	@Test
	public  void invalid_city_name() {
	  assertEquals(false,ServiceUtil.isStringOnlyAlphabet("3LOS"));
	}
	
	@Test
	public void capitalize_City_Name() {
		assertEquals("Leeds",ServiceUtil.capitalize("LEEDS"));
	}
	
	@Test
	public void filter_city_within_50_KM() throws Exception {
	 List<UserDTO> filteredUsers =	userService.filterByDistance(dtoList,50,"KM");
	 assertEquals(filteredUsers.size(), 2);
	 assertEquals(filteredUsers.get(0).getFirst_name(),"Babu");
	}
	
	@Test
	public void filter_city_within_50_Miles() throws Exception {
	 List<UserDTO> filteredUsers =	userService.filterByDistance(dtoList,50,"Miles");
	 assertEquals(filteredUsers.size(), 2);
	 assertEquals(filteredUsers.get(0).getFirst_name(),"Babu");
	}
	
	@Test
	public void filter_city_within_50_Meter() throws Exception {
	 List<UserDTO> filteredUsers =	userService.filterByDistance(dtoList,50,"Meter");
	 assertEquals(filteredUsers.size(), 0);
	}
	
	@Test
	public void filter_city_600_KM_boundary() throws Exception {
	 List<UserDTO> filteredUsers =	userService.filterByDistance(dtoList,600,"KM");
	 assertEquals(filteredUsers.size(), 4);
	 assertEquals(filteredUsers.get(2).getFirst_name(),"Ganesh");
	}
	
	
	@Test
	public void should_return_london_city_users() throws Exception {
		List<UserDTO> filteredUsers = userService.getUserByCity(cityName);
		assertThat(filteredUsers.size(), greaterThan(0));
	}
	
	@Test
	public void should_throw_invalid_city_exception() throws Exception {
		Assertions.assertThrows(InvalidCityException.class, ()->userService.getUserByCity(""));
	}

	@Test
	public void should_throw_user_not_cound_exception() throws Exception {
		Assertions.assertThrows(FeignException.NotFound.class, ()->userService.getUserById("1a"));
	}

	
	@Test
	public void getUserById() throws Exception {
		UserDTO user = userService.getUserById("1");
		assertThat(user, notNullValue());
	}
	
	
	
}
