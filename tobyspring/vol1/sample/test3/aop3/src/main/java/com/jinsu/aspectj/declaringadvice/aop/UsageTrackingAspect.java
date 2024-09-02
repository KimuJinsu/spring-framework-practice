package com.jinsu.aspectj.declaringadvice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

import com.jinsu.aspectj.declaringadvice.model.DefaultUsageTracked;
import com.jinsu.aspectj.declaringadvice.model.UsageTracked;

@Aspect
@Component
public class UsageTrackingAspect {

	@DeclareParents(value="com.jinsu.aspectj.declaringadvice.service.*+", 
			defaultImpl = DefaultUsageTracked.class)
	public static UsageTracked mixin;
	
	
	@Before("execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..)) "
			+ "&& this(usageTracked)")
	public void trackUsage(UsageTracked usageTracked) {
		usageTracked.incrementUseCount();
		System.out.println("Usage count incremented: " + usageTracked.getUseCount());
	}
}