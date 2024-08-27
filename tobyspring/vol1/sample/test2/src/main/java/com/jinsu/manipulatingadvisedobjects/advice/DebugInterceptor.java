package com.jinsu.manipulatingadvisedobjects.advice;

import static java.lang.System.out;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DebugInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		out.println("DebugInterceptor:Before method " +
				invocation.getMethod().getName());
		Object retVal = invocation.proceed();
		out.println("DebugInterceptor:After method " +
				invocation.getMethod().getName());
		
		return retVal;
	}

}
