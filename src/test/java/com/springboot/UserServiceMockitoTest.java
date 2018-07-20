package com.springboot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import com.springboot.controller.UserController;
import com.springboot.exception.InvalidDateException;
import com.springboot.exception.NotActiveUserException;
import com.springboot.exception.UserAlreadyExistsException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.Response;
import com.springboot.model.User;
import com.springboot.service.UserServicce;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)

public class UserServiceMockitoTest 
{
	@MockBean
	UserServicce userMockService;
		
	@InjectMocks
	UserController user_controller;
	
	private MockMvc mockMvc;
	
	Response res=new Response();
	
	@Before
    public void setUp()
	{
        // for controller set up
        userMockService = mock(UserServicce.class);
     
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(user_controller).build();       
    }
	
	@Test
	public void testMockSave() throws Exception
	{	
		User user=new User("12","Paul","RajRam","ram123@gmail.com",741852,new Date("10-JUN-1993"));
		String expectedoutput="12";
		Response res=new Response();
			
		when(userMockService.saveUser(user)).thenReturn(res);  
		Response actualresult = user_controller.addUser(user);
		assertEquals(expectedoutput, user.getId());
	}
	
	@Test
	public void testMockUpdate() throws Exception
	{
		User user=new User("220","Shar","Gayu","ushapaul@gmail.com",85294,new Date("20-Oct-2002"));
		System.out.println("Before update "+ user.getPinCode());
		user.setPinCode(632005);
		user.setBirthDate(new Date("12-JUN-1993"));
		
		String expectedoutput="220";
		when(userMockService.updateUser(user, user.getId())).thenReturn(res);
		Response actualUpdateResult=user_controller.userUpdate(user, user.getId());
		System.out.println("User pincode "+user.getPinCode());
		assertEquals(expectedoutput,user.getId());
	}
	
	
	@Test
	public void testMockPostSave() throws Exception
	{
		User user=new User("220","Shar","Gayu","sha220@gmail.com",774145,new Date("02-JUN-1950"));
		
		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.post("/createTest")
				.header("testHeader",
						"headerValue")
				.contentType(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());		
	}
	
	@Test
	public void testNegativeMockSave() throws Exception
	{	
		User user=new User("12358","San","RajRam","29_sanju@gmail.com",741852,new Date("10-JUN-1993"));
		String expectedoutput="User already exists";
		
		when(userMockService.saveUser(user)).thenThrow(new UserAlreadyExistsException("User already exists"));
		try {
		Response actualresult = user_controller.addUser(user);
		}catch(UserAlreadyExistsException e) {
			System.out.println("Response Message For Save User : "+e.getMessage());
			Assert.assertEquals(expectedoutput, e.getMessage());
		}
	}
	
	@Test
	public void testNegativeInactiveUser() throws Exception
	{	
		User user=new User("12353","Sharmi","Paul","sharmiPa123513@gmail.com",632009,new Date("21-Jul-1982"));
		String expectedoutput="not an active user";
		
		when(userMockService.updateUser(user, user.getId())).thenThrow(new NotActiveUserException("not an active user"));
		try{
			Response actualUpdateResult=user_controller.userUpdate(user, user.getId());
		}catch(NotActiveUserException e)
		{
			System.out.println("Response Message For Inactive User : "+e.getMessage());
			Assert.assertEquals(expectedoutput, e.getMessage());
		}
	}

}
