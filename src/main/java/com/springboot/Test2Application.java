package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class Test2Application 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(Test2Application.class, args);
	}
}
