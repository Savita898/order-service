package com.savita.order.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorDetails> orderNotFoundExceptionHandler(OrderNotFoundException orderException)
	{
		ErrorDetails errorDetails= new ErrorDetails(orderException.getMessage(),new Date());
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception exception)
	{
		ErrorDetails errorDetails= new ErrorDetails(exception.getMessage(),new Date());
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	}	

}
