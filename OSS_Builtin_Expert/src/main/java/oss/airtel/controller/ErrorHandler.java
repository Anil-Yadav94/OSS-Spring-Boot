package oss.airtel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import oss.airtel.Exceptions.ApiError;
import oss.airtel.Exceptions.CustomerException;
import oss.airtel.Exceptions.NAFaultStringException;
import oss.airtel.Exceptions.NAServerException;


@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);
	
		@Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String error = "Internal Server Error.";
	       return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	   }

	   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		   log.info(apiError.toString());
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
	   
	   @ExceptionHandler({CustomerException.class, NAFaultStringException.class, NAServerException.class})
	   public ResponseEntity<ApiError> customerHandleNotFound(Exception ex, WebRequest request) {
	        ApiError apiError=new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Please try again or check input.", ex  );
	        log.info(apiError.toString());
		    return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
	   
	  
//	   @ExceptionHandler(AsyncRequestTimeoutException.class)
//	    public final ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, WebRequest request) {
//		   ApiError apiError=new ApiError(HttpStatus.REQUEST_TIMEOUT, "Request Timeout.", ex  );
//		   return new ResponseEntity<>(apiError, apiError.getStatus());
//	    }

	   
}
