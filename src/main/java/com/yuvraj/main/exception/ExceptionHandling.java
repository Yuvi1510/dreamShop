package com.yuvraj.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({AlreadyExistsException.class})
	public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsExceptions(AlreadyExistsException ex){
		return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({InvalidCredentialsException.class})
	public ResponseEntity<ExceptionResponse> handleInvalidCredentialsExceptions(InvalidCredentialsException ex){
		return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()),HttpStatus.UNAUTHORIZED);
	}
}
