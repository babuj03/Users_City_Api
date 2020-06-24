package com.user.info.web.rest.errors;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ExceptionResponse {
	
	String message;
	String path;
	LocalDateTime  timestamp =LocalDateTime.now();
	int status;
	
	
	public ExceptionResponse() {
		super();
	}
	public ExceptionResponse(String message, String path, LocalDateTime timestamp, int status) {
		super();
		this.message = message;
		this.path = path;
		this.timestamp = timestamp;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + ", path=" + path + ", timestamp=" + timestamp + ", status=" + status
				+ "]";
	}
	
	
	
	

}
