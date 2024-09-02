package com.jinsu.aspectj.declaringadvice.aop;

import static java.lang.System.out;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jinsu.aspectj.declaringadvice.annotation.Auditable;

@Aspect
@Component
public class AuditAspect {
	
	// @target은 프록시 객체와 관련이 깊으며, 
	//     프록시 객체가 위임하는 실제 객체(타겟 객체)에 어노테이션이 적용된 경우에 어드바이스가 실행됩니다.
	// @within은 프록시 객체가 아닌 클래스 자체에 대한 어노테이션을 기준으로 어드바이스를 적용합니다.

	// @within : 특정 어노테이션이 클래스 레벨에 적용되었을 때, 해당 클래스의 모든 메서드에 매칭!
	@Pointcut("@within(com.jinsu.aspectj.declaringadvice.annotation.Auditable)")
	public void withinAuditableClsass() {}
	
	
	// @target : 특정 어노테이션이 적용된 객체의 모든 메서드에 매칭...
	//           이 포인트컷은 런타임 시점에, 프록시 객체가 생성될 때
	//           해당 객체의 클래스가 @Auditable 어노테이션을 가지고 있는지 확인.
	//           만약 그러하다면, 해당 객체의 모든 메서드가 포인트컷에 매칭!
	@Pointcut("@target(com.jinsu.aspectj.declaringadvice.annotation.Auditable)")
	public void targetAuditableClass() {}
	
	// @annotation: 특정 어노테이션이 적용된 메서드라는 점!
	@Pointcut("@annotation(auditable)")
	public void methodWithAuditableAnnotation(Auditable auditable) {}
	
	// com.intheeast.aspectj.declaringadvice.annotation
	@Pointcut("execution(* com.jinsu.aspectj.declaringadvice.service.*.*(..)) && "
			+ "@args(com.jinsu.aspectj.declaringadvice.annotation.Auditable)")
	public void methodWithAuditableArgs() {}
	
	/*
	@Before("withinAuditableClsass()")
	public void logWithinAuditableClass(JoinPoint jp) {
		out.println("logWithinAuditableClass Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
	}
	
	@Before("targetAuditableClass()")
	public void logtargetAuditableClass(JoinPoint jp) {
		out.println("logtargetAuditableClass Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());	
	}
	*/
	
	@Before("methodWithAuditableAnnotation(auditable)")
	public void logAudiOperation(JoinPoint jp, Auditable auditable) {
		out.println("logAudiOperation Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
		out.println("Operation: " + auditable.value());
	}
	
	@Before("methodWithAuditableArgs()")
	public void logMethodWithAuditableArgs(JoinPoint jp) {
		out.println("logMethodWithAuditableArgs Advice");
		out.println("Class: " + jp.getTarget().getClass().getName());
		out.println("Method: " + jp.getSignature().getName());
	}
}