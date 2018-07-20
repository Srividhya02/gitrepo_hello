package com.springboot;

import org.junit.Test;
import com.springboot.aspect.SpringLoggingHelper;

public class AopAspectTest 
{
	 @Test
	 public void testPerformTask() throws Exception
	 {
        SpringLoggingHelper logBackDemo = new SpringLoggingHelper();
        logBackDemo.HelpMethod();
     }
}
