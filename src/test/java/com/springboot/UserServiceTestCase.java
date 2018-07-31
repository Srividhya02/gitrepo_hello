package com.springboot;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.exception.NotActiveUserException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.exception.UserAlreadyExistsException;
import com.springboot.model.Response;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.service.UserServicce;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase 
{	
	@Autowired
	UserServicce userService;
	
	@Autowired
	UserRepository userRepo;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testSaveUser() throws UserAlreadyExistsException
	{

		User user = new User("12355","Sharmi","Paul",
				"sharmiPa12355@gmail.com",632009,new Date("21-JUL-1982"));

		Response saveUser =userService.saveUser(user);
		saveUser.setResMessage("New User created Successfully");
		saveUser.setId(user.getId());
		System.out.println("User***********"+saveUser.getResMessage());
	}
	
	@Test
	public void TestUpdateUser() throws UserNotFoundException, NotActiveUserException
	{
		User user=new User();
		user.setId("12354");
		user.setPinCode(632002);
		Date d = new Date("03-JUN-1993");
		user.setBirthDate(d);
		
		if(userRepo.existsByEmail(user.getEmail()) && 
				user.isActive()==false)
		{
			exception.expect(NotActiveUserException.class);
			exception.expectMessage("not an active user");
		}
		
		Response updateUser = userService.updateUser(user, user.getId());
		updateUser.setResMessage("Updated successfully");
		updateUser.setId(user.getId());
		System.out.println("User pincode is "+user.getPinCode() + "\n" + 
					"User DOB: " + user.getBirthDate());
	}
	
	@Test
	public void TestDeleteUser() throws UserNotFoundException
	{
		User user=new User();
		user.setId("12353");
		user.setEmail("sharmiPa123513@gmail.com");
		Response deleteUser= userService.deleteByUserId(user.getId());
		deleteUser.setResMessage("User Deactivated successfully");
		deleteUser.setId(user.getId());
		
	} 
}
