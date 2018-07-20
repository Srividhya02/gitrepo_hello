package com.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import com.springboot.model.User;

@Aspect
@Component
public class UserAspect
{
	private Logger logger = LoggerFactory.logger(this.getClass());
	
	@Pointcut("execution(* com.springboot.*.*.*(..))")
	public void generalPointcut()
	{
		
	}

	//@Before(value="execution(* com.springboot.service.UserServicce.*(..))")
	@Before("generalPointcut()")
	public void beforeAdvice(JoinPoint joinPoint)
	{
		System.out.println("BEFORE METHOD CALL");
		logger.info(joinPoint.getTarget().getClass().getSimpleName()+" : " 
					+joinPoint.getSignature().getName());
	}	
	
	@After("generalPointcut()")
	public void afterAdvice(JoinPoint joinPoint)
	{
		System.out.println("AFTER METHOD CALL");
		logger.info(joinPoint.getTarget().getClass().getSimpleName()+" : " +joinPoint.getSignature().getName());
	}
	
	@AfterThrowing(pointcut="generalPointcut() throws Exception", throwing="ex")
	public void exceptionLog(JoinPoint joinpoint, Exception ex) throws Exception
	{
		System.out.println("EXCEPTION METHOD CALL");
		logger.error(joinpoint.getTarget().getClass().getSimpleName()+" : " +joinpoint.getSignature().getName()
				+" : " + ex.getMessage());
	}
	
	@Around("generalPointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable
	{
		System.out.println("AROUND BEFORE EXECUTION " + joinPoint.getClass().getName());
		Object value=null;
		try
		{
			value=joinPoint.proceed();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		System.out.println("AROUND AFTER EXECUTION " + joinPoint.getClass().getName());
		return value;
	}
	
	@Around("@annotation(com.springboot.annotation.UserdefinedAnnotation)")
	public Object logDataAccess(ProceedingJoinPoint joinPoint) throws Throwable
	{
		logger.info("Before invoking the target");
	    Object invocationResult = joinPoint.proceed();
	    logger.info("After invoking the target");
	    System.out.println("InvocationResult " +invocationResult);
	    return invocationResult;
	}
}
