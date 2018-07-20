package com.springboot.exception;

public class UserAlreadyExistsException extends Exception
{
//	private String resourceId;
	
	public UserAlreadyExistsException(String exception) 
	{
		super(exception);
//		this.resourceId = resourceId;
	}
}
