package com.user.info.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.user.info.service.dto.UserDTO;
import com.user.info.service.proxy.UserServiceProxy;
import com.user.info.util.ServiceUtil;
import com.user.info.web.rest.errors.InvalidCityException;
import com.user.info.web.rest.errors.UserNotFoundException;

import feign.FeignException;

@Service
public class UserService {

	private UserServiceProxy userServiceProxy;

	private MessageSource messageSource;
	
	public UserService(UserServiceProxy userServiceProxy,MessageSource messageSource) {
		this.userServiceProxy = userServiceProxy;
		this.messageSource=messageSource;
	}

	public List<UserDTO> users() {
		return userServiceProxy.getUsers();
	}

	public UserDTO getUserById(String id) {
		
		UserDTO user=null;
		try {
			user = userServiceProxy.getUserById(id);
		} catch (FeignException.NotFound ex) {
			throw new UserNotFoundException(
					messageSource.getMessage("error.user.not.found", new Object[] { id }, LocaleContextHolder.getLocale()));
		}
		return user;
	}

	public List<UserDTO> getUserByCity(String city) {
		List<UserDTO> users = null;
		
		if(!city.isEmpty() && ServiceUtil.isStringOnlyAlphabet(city)) {
		  users = userServiceProxy.getUserByCity(ServiceUtil.capitalize(city));
		}else {
			throw new InvalidCityException(
					messageSource.getMessage("error.invalid.city.name", new Object[] { city }, LocaleContextHolder.getLocale()));
		}
		
		return users;
	}
	


	public List<UserDTO> filterByDistance(List<UserDTO> users, float distance, String unit) {
			List<UserDTO> result = users.parallelStream().filter(user -> {
			double calDistance =	ServiceUtil.distance( user.getLatitude(),
						user.getLongitude(),unit);
			if (calDistance <= distance)
				return true;
			else
				return false;
		}).collect(Collectors.toList());

		return result;
	}
}
