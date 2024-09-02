package com.jinsu.aspectj.declaringadvice.aop;

import static java.lang.System.out;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..))")
	public void logBeforeMethod(JoinPoint jp) {
		out.println("logBeforeMethod Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());		
	}
	
	@AfterReturning(
			pointcut = "execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..))",
			returning = "result"
			)
	public void logAfterReturning(JoinPoint jp, Object result) {
		out.println("logAfterReturning Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
		out.println("Method return: " + result);
	}
	
	@AfterThrowing(
			pointcut = "execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..))",
			throwing = "error"
			)
	public void logAfterThrowing(JoinPoint jp, Throwable error) {
		out.println("logAfterThrowing Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
		out.println("Method threw exception: " + error);
	}
	
	@After("execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..))")
	public void logAfterFinally(JoinPoint jp) {
		out.println("logAfterFinally Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}