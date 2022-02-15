package com.gl.usermicroservice.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public final ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.CONFLICT.value(), ex.getMessage());
	
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InputDataException.class)
	public final ResponseEntity<Object> handleInputDataException(InputDataException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
	
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<Object> handleCredencialException(InputDataException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
	
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleInputDataException(AccessDeniedException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
	
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	//AccessDeniedException
	/*
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().toString());
		
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}*/
}

