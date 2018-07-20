package com.springboot.exception;

public class NotActiveUserException extends Exception
{
	private String resourceId;
	
	public NotActiveUserException(String exception) 
	{
		super(exception);
		this.resourceId = resourceId;
	}
}
