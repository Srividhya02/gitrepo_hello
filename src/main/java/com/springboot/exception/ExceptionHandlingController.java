package com.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController 
{
	 @ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> userNotFound(UserNotFoundException e)
	{
		 ErrorDetails error=new ErrorDetails();
		 error.setErrorCode("404");
		 error.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
	}
	 
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> userExists(UserAlreadyExistsException e)
	{
		 ErrorDetails error=new ErrorDetails();
		 error.setErrorCode("404");
		 error.setErrorMessage(e.getMessage());
		 return new ResponseEntity<ErrorDetails>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(NotActiveUserException.class)
	public ResponseEntity<ErrorDetails> userInactive(NotActiveUserException e)
	{
		 ErrorDetails error=new ErrorDetails();
		 error.setErrorCode("404");
		 error.setErrorMessage(e.getMessage());
		 return new ResponseEntity<ErrorDetails>(error, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> invalidInput(MethodArgumentNotValidException ex)
	{
		BindingResult result=ex.getBindingResult();
		ErrorResponse response=new ErrorResponse();
		response.setErrorCode("Validation Error");
		response.setErrorMessage("Check validation");
		response.setErrors(ValidationUtil.fromBindingErrors(result));
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ErrorDetails> invalidDate(InvalidDateException e)
	{
		 ErrorDetails error=new ErrorDetails();
		 error.setErrorCode("404");
		 error.setErrorMessage(e.getMessage());
		 return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
}
