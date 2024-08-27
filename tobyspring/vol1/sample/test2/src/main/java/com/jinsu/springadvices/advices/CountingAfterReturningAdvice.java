package com.jinsu.springadvices.advices;

import java.lang.reflect.Method;
import static java.lang.System.out;
import org.springframework.aop.AfterReturningAdvice;

public class CountingAfterReturningAdvice implements AfterReturningAdvice {

	private int count;
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
				count++;
				out.println("After method:" +
				method.getName() +
				", return value: " +
				returnValue + ", count=" + count); 
				
	}
	
	public int getCount() {
		return count;
	}

}
