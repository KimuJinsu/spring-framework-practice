package com.jinsu.aspectj.declaringadvice.aop;

import static java.lang.System.out;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jinsu.aspectj.declaringadvice.model.Account;

@Aspect
@Component
public class AccountValidationAspect {

	@Pointcut("execution(* com.jinsu.aspectj.declaringadvice.dao.*.*(..)) && args(account,..)")
	public void accountDataAccessOperation(Account account) {}
	
	@Before("accountDataAccessOperation(account)")
	public void validateAccount(JoinPoint jp, Account account) {
		
		out.println("validateAccount Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
		
		if (account.getBalance() < 0) {
			throw new IllegalArgumentException("Account balance cannot be negative");
		}
		System.out.println("Account validation passed for: " + account);
	}
	
}