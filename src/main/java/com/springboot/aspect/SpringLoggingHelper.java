package com.springboot.aspect;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.model.User;
import com.springboot.service.UserServicce;

public class SpringLoggingHelper
{
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
		UserServicce userService;
	 
	 public void HelpMethod()
	 {
		 	logger.debug("This is a debug message");
	        logger.info("This is an info message");
	        logger.warn("This is a warn message");
	        logger.error("This is an error message");
	 }
}
