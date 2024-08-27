package com.jinsu.springadvices.advices;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import static java.lang.System.out;

public class DebugInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		out.println("Before: invocation=[" + invocation + "]");
		Object retVal = invocation.proceed();
		out.println("Invocation returned with value: " + retVal);
		return retVal;
	}

}
