package com.springboot.exception;


public class UserNotFoundException extends Exception
{
	private String resourceId;
	
	public UserNotFoundException(String exception) 
	{
		super(exception);
		this.resourceId = resourceId;
	}
}
