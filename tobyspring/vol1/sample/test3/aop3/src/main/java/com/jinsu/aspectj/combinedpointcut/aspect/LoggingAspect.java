package com.jinsu.aspectj.combinedpointcut.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	@Pointcut("com.jinsu.aspectj.combinedpointcut.pointcuts.CommonPointcuts.inWebLayer()" 
			+ "&& execution(public * *(..))")
	public void publicWebLayerOperation() {}
	
	@Pointcut("com.jinsu.aspectj.combinedpointcut.pointcuts.CommonPointcuts.businessService()" 
			+ "&& !com.jinsu.aspectj.combinedpointcut.pointcuts.CommonPointcuts.inDataAccessLayer)")
	public void transactionServiceOperation() {}
	
	@Pointcut("publicWebLayerOperation() || transactionServiceOperation() ")
	public void webOrTransactionServiceOperation() {
		
	}
	
	// JoinPoint 인터페이스 name에 주의하자!!!
	// org.aopalliance.intercept 패키지의 Joinpoint 혼동하지 말것!!!
	// 우리가 사용할 조인포인트는 org.aspectj.lang.JoinPoint 임!!!
	@Before("publicWebLayerOperation()")
	public void logBeforePublicWebOperation(JoinPoint joinpoint) {
		System.out.println("Method: " + joinpoint.getSignature().getName());
	}
	
	
	
	
}



	
