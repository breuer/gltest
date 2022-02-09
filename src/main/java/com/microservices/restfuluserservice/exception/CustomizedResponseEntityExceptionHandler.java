package com.microservices.restfuluserservice.exception;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex, WebRequest request) {
		//String msg = ex.getConstraintViolations().stream()
		//	.map(cv -> cv.getMessage())
		//	.collect(Collectors.joining(". "));
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), "msg");
		
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
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

