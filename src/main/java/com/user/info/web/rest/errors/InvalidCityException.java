package com.user.info.web.rest.errors;
public class InvalidCityException  extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public InvalidCityException(String message){
		super(message);
		this.errorMessage = message;
	}

}
