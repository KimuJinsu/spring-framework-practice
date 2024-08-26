package com.jinsu.pointcutapi.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ExceptionHandlingAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		try {
			return invocation.proceed();
		}
		catch (Exception e) {
			System.out.println("Executing caught in method: " +
					invocation.getMethod().getName() +
					", exception: " + e.getMessage());
			throw e;
		}
		
	}

}
