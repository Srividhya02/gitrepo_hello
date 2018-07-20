package com.springboot.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JSONSerialize extends JsonSerializer<Date> 
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2) 
							throws IOException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}

}
