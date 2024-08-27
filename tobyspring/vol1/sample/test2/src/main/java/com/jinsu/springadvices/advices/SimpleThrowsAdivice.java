package com.jinsu.springadvices.advices;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class SimpleThrowsAdivice implements ThrowsAdvice {
	
	public void afterThrowing(Method method,
			Object[] args,
			Object target, 
			Exception e) {
	    System.out.println("Exception in method: " + method.getName() + ", exception: " + e.getMessage());
	    
	    
	}
}
