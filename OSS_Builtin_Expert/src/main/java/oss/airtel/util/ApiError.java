package oss.airtel.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String cause;
	
	private ApiError() {
	    timestamp = LocalDateTime.now();
	}
	
	ApiError(HttpStatus status) {
	    this();
	    this.status = status;
	}
	
	ApiError(HttpStatus status, Throwable ex) {
	    this();
	    this.status = status;
	    this.message = "Unexpected error";
	    this.cause = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
	    this();
	    this.status = status;
	    this.message = message;
	    this.cause = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getCause() {
		return cause;
	}	   
	   
}
