package com.jinsu.pointcutapi.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import static java.lang.System.out;


public class LoggingAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		out.println("Before method: " + invocation.getMethod());
		Object result = invocation.proceed();
		out.println("After method: " + invocation.getMethod());

		
		return result;
	}

}
