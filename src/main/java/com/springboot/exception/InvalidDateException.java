package com.springboot.exception;

import java.io.IOException;

public class InvalidDateException extends IOException
{
	private String resourceId;
	
	public InvalidDateException(String exception) 
	{
		super(exception);
		this.resourceId = resourceId;
	}
}
