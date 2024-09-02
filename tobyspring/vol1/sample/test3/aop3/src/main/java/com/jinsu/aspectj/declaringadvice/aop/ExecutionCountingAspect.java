package com.jinsu.aspectj.declaringadvice.aop;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionCountingAspect {

	// Atomic : 원자성...Thread가 원자성을 지킬 수 있도록 해준다.
	// int a[전역변수] = 0;
	// sleep(0);
	// a = 10; // OS가 Thread에게 할당한 CPU Time Slice가 경과[만료]하면,,,
	private AtomicInteger executionCount = new AtomicInteger(0);
	
	@Around("execution(* com.jinsu.aspectj.declaringadvice.service.MyService.*(..))")
	public Object countExecutions(ProceedingJoinPoint pjp) throws Throwable {
		int currentCount = this.executionCount.incrementAndGet();
		
		System.out.println("countExecutions: before executionCount: " + currentCount);
		
		Object ret = pjp.proceed();
		
		System.out.println("countExecutions: after executionCount: " + getExecutionCount());
		
		return ret;
	}
	
	public int getExecutionCount() {return this.executionCount.get();}
}