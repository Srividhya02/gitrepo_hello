package com.springboot.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.springboot.exception.InvalidDateException;

@Component
public class JsonDateDeSerializer extends JsonDeserializer<Date>
{	
	@Override
	public Date deserialize(JsonParser paramJsonParser,
	            DeserializationContext paramDeserializationContext)
	            throws IOException
	{
			SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yyyy",
					Locale.ENGLISH);
			dateFormat.setLenient(false); 
			
	        String str = paramJsonParser.getText();
	        if (str == null || str.trim().length() == 0)
	        {
	            return null;
	        }
	        Date date = null;
	      
	        try 
	        {
	        	date= dateFormat.parse(str);
	        } 
	        catch (ParseException e)
	        {
	        	 throw new InvalidDateException("Invalid Date format");
	        }
	        return date;
	   	}
}
