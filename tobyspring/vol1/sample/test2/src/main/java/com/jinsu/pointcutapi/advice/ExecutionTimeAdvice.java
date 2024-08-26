package com.jinsu.pointcutapi.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class ExecutionTimeAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long startTImeMills = System.currentTimeMillis();
		
		try {
		invocation.proceed();
		} finally {
			long timeTakeMills = System.currentTimeMillis() - startTImeMills;
			System.out.println(
					"Executing time of " + 
					invocation.getMethod().getName() +
					" ::" + timeTakeMills + "ms");
			
		}
		
				return startTImeMills;
	}

}
