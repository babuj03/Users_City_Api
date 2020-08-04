package com.user.info.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.user.info.service.dto.UserDTO;
import com.user.info.service.proxy.UserServiceProxy;
import com.user.info.util.ServiceUtil;
import com.user.info.web.rest.errors.InvalidCityException;
import com.user.info.web.rest.errors.UserNotFoundException;

import feign.FeignException;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import net.sf.geographiclib.GeodesicMask;

@Service
public class UserService {

	@Value("${city.latitude}")
	double cityLatitude;
    
	@Value("${city.longitude}") 
	double cityLongitude;

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
		
		
		try {
			return userServiceProxy.getUserById(id);
		} catch (FeignException ex) {
			if(ex.
			throw new UserNotFoundException(
					messageSource.getMessage("error.user.not.found", new Object[] { id }, LocaleContextHolder.getLocale()));
		}
		
	}

	public List<UserDTO> getUserByCity(String city) {
		
		
		if(!city.isEmpty() && ServiceUtil.isStringOnlyAlphabet(city)) {
		 return userServiceProxy.getUserByCity(ServiceUtil.capitalize(city));
		}else {
			throw new InvalidCityException(
					messageSource.getMessage("error.invalid.city.name", new Object[] { city }, LocaleContextHolder.getLocale()));
		}
	}
	

	public List<UserDTO> filterByDistance(List<UserDTO> users, float distance, String unit) {
			List<UserDTO> result = users.parallelStream().filter(user -> {
			double calDistance =	ServiceUtil.distance(cityLatitude, cityLongitude, user.getLatitude(),
						user.getLongitude(),unit);
			return calDistance <= distance;
			
		}).collect(Collectors.toList());

		return result;
	}
}
