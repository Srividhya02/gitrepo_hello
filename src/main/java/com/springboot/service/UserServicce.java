package com.springboot.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.annotation.UserdefinedAnnotation;
import com.springboot.exception.NotActiveUserException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.exception.UserAlreadyExistsException;
import com.springboot.model.Response;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;

@Service
public class UserServicce 
{
	@Autowired
	UserRepository user_repository;
	
	@UserdefinedAnnotation       //  user defined annotation
	public Response saveUser(User user) throws UserAlreadyExistsException
	{  
		Response res=new Response();
		
		 if(user_repository.existsByEmail(user.getEmail()) &&
				user_repository.findByEmail(user.getEmail()).isActive()) 
		{
			throw new UserAlreadyExistsException("User already exists");
		} 
		else
		{
			user.setActive(true);
			user_repository.save(user);
			res.setResMessage("New User created Successfully");
			res.setId(user.getId());
		}
		 return res;
		//return new ResponseEntity<>("User "+ user.getId()+ " created", HttpStatus.OK);
	}

	@UserdefinedAnnotation
	public List<User> getUserDetails() 
	{
		return (List<User>) user_repository.findAll();
	}
	
	
	public User findByUserId(String id)
	{
		return user_repository.findById(id).orElse(null);
	}

	public Response updateUser(User user,String id) throws UserNotFoundException, NotActiveUserException
	{
		Response res=new Response();
		
		User usr=findByUserId(id);
		if(usr.getId()==null)
		{
			throw new UserNotFoundException("User not found");
		}
		else if(usr.getId()!=null && usr.isActive()==false)
		{
			throw new NotActiveUserException("not an active user");
		}
		else if(usr.getId().equals(user.getId()) && usr.getfName().equals(user.getfName()) &&
				usr.getlName().equals(user.getlName())&& usr.getEmail().equals(user.getEmail()))
		{
			usr.setPinCode(user.getPinCode());
			usr.setBirthDate(user.getBirthDate());
			res.setResMessage("Updated successfully");
			res.setId(user.getId());
		} 
		else
		{
			res.setResMessage("Only Pincode and DOB can be updated");
			res.setId(user.getId());
		}
		user_repository.save(usr);
		return res;
	//	return new ResponseEntity<String>("User id "+ " " +user.getId()+" "+"Updated successfully", HttpStatus.OK);
	}
	
	public Response deleteByUserId(String id) throws UserNotFoundException 
	{
		Response res=new Response();
		
		User usr=findByUserId(id);
		if(id==null)
		{
			throw new UserNotFoundException("User not found");
		}
		else
		{
			Boolean isActive_user=usr.isActive();	
			if(isActive_user==true)
			{
				usr.setActive(false);
			}
		}
		user_repository.save(usr);
		return res;
	//	return new ResponseEntity<String>(id +" " + "Deleted" , HttpStatus.OK);
	}
}
