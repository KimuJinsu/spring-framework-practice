package com.jinsu.aspectj.declaringadvice.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {
	
	@Pointcut("within(com.jinsu.aspectj.declaringadvice.service..*)")
	public void inDataAccessLayer() {
		
	}

}