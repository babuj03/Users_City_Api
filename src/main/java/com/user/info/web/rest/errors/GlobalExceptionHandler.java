package com.user.info.web.rest.errors;



import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

 
  @ExceptionHandler(FeignException.BadRequest.class)
	public ResponseEntity<ExceptionResponse> handleFeignBadRequestException(FeignException ex, WebRequest request, HttpServletResponse response) {
	  ExceptionResponse exceptionResponse = new ExceptionResponse(
			  ex.getMessage(),
			  request.getDescription(false),
			  LocalDateTime.now(),
			  HttpStatus.BAD_REQUEST.value());
	 
	  
	  return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleFeignUserNotFoundException(UserNotFoundException ex, WebRequest request, HttpServletResponse response) {
	  ExceptionResponse exceptionResponse = new ExceptionResponse(
			  ex.getMessage(),
			  request.getDescription(false),
			  LocalDateTime.now(),
			  HttpStatus.NOT_FOUND.value());
	  
	  return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(InvalidCityException.class)
  public ResponseEntity<ExceptionResponse> handleFeignUserNotFoundException(InvalidCityException ex, WebRequest request, HttpServletResponse response) {
	  ExceptionResponse exceptionResponse = new ExceptionResponse(
			  ex.getMessage(),
			  request.getDescription(false),
			  LocalDateTime.now(),
			  HttpStatus.UNPROCESSABLE_ENTITY.value());
	  
	  return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }
  
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
	  ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	  return new ResponseEntity<Object>(exceptionResponse,  HttpStatus.INTERNAL_SERVER_ERROR);
  
   }
 
}





