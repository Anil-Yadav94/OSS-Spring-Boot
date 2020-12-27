package oss.airtel.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import oss.airtel.util.ApiError;
import oss.airtel.util.CustomException;


@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

		@Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String error = "Internal Server Error.";
	       return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	   }

	   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
	   
	   @ExceptionHandler(CustomException.class)
	   public ResponseEntity<ApiError> customHandleNotFound(Exception ex, WebRequest request) {

	        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST, "Kindly Check your input", ex  );
			return new ResponseEntity<>(apiError, apiError.getStatus());
	    }
}
