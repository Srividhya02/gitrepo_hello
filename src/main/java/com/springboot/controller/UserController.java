package com.springboot.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.Test2Application;
import com.springboot.annotation.UserdefinedAnnotation;
import com.springboot.aspect.SpringLoggingHelper;
import com.springboot.exception.NotActiveUserException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.exception.UserAlreadyExistsException;
import com.springboot.model.Response;
import com.springboot.model.User;
import com.springboot.service.UserServicce;

@RestController
@RequestMapping
public class UserController 
{
	@Autowired
	UserServicce userService;
	
	private final Logger logger  = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/log",method=RequestMethod.GET)
	public String index()
	{
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
	        return "index";
	}
	
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
	@UserdefinedAnnotation
	public Response addUser( @Valid @RequestBody User user) throws UserAlreadyExistsException
	{
		logger.debug("*** NEW USER ID ***" +user.getId()+ " \n" +
				"*** EMAIL ***" + user.getEmail()+ " \n" + "*** FIRST NAME ***" + user.getfName());
		return userService.saveUser(user);	
	}
	
	@RequestMapping(value="/userDetails",method=RequestMethod.GET)
	@UserdefinedAnnotation
	public ResponseEntity<List<User>> userDetails()
	{
		List userDetails=userService.getUserDetails();
		logger.debug("*** LIST SIZE IS " +userDetails.size());
		return new ResponseEntity<List<User>>(userDetails, HttpStatus.OK);
	}
	
	@RequestMapping(value="/userId/{id}",method=RequestMethod.GET)
	public User getUserById(@PathVariable("id") String id) throws UserNotFoundException 
	{
		User user=userService.findByUserId(id);
		if(user==null)
		{
			throw new UserNotFoundException("User not found");
		}
		else
		return userService.findByUserId(id);
	}
	
	@RequestMapping(value="/updateUser/{id}",method=RequestMethod.PATCH)
	public Response userUpdate(@Valid @RequestBody  User currentUser,@PathVariable String id) throws UserNotFoundException, NotActiveUserException
	{
		Response usr1=userService.updateUser(currentUser,id);
	//	User usr=userService.findByUserId(id);
		
		if(usr1==null)
		{
			throw new UserNotFoundException("User not found");
		}
		else
			return usr1;
	//	return new ResponseEntity<String>("User id "+ " " +currentUser.getId()+" "+"Updated successfully",HttpStatus.OK);
	}

	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public Response delete(@PathVariable String id) throws UserNotFoundException
	{
		Response res=new Response();
		User usr=userService.findByUserId(id);
		
		if(usr==null)
		{
		  throw new UserNotFoundException("User not found");
		}
		else if(usr!=null && usr.isActive()==false)
		{
			logger.error("NOT ACTIVE USER");
			throw new UserNotFoundException("not an active user");
		}
		else
		{
			logger.debug("*** DELETE USER ID ***" +usr.getId());
			userService.deleteByUserId(id);
			res.setResMessage("User Deactivated successfully");
			res.setId(id);
		}
		return res;
	//	return new ResponseEntity<String>(id +" " + "Deleted" , HttpStatus.OK);
	} 
	

	@RequestMapping("/createTest")
	@ResponseBody
	public ResponseEntity<String> addTestUser(RequestEntity<User> requestEntity)
	{
		User user = new User();
		user.setId("15");
		user.setfName("Sharmila");
		user.setlName("Paulraj");
		user.setEmail("sharmiPaul@gmail.com");
		user.setPinCode(632009);

		Date d = new Date("21-JUL-1982");
		user.setBirthDate(d);
		user.setActive(true);
		
        System.out.println("request body: " + user);
        System.out.println("request headers " + requestEntity.getHeaders());
        System.out.println("request method : " + requestEntity.getMethod());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Success",
                                                                     headers,
                                                                     HttpStatus.OK);
        return responseEntity;
	}
}
