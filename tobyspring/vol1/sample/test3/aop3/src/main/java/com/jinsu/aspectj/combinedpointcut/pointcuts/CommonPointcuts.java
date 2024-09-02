package com.jinsu.aspectj.combinedpointcut.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {
	
	// web 패키지와 그 하위 패키지에 있는 모든 조인 포인트
		@Pointcut("within(com.jinsu.aspectj.combinedpointcut.web..*)")
		public void inWebLayer() {}
		
		// service 패키지와 그 하위 패키지에 있는 모든 조인 포인트
		@Pointcut("within(com.jinsu.aspectj.combinedpointcut.service)")
		public void inSerivceLayer() {}
		
		@Pointcut("within(om.jinsu.aspectj.combinedpointcut.dao)")
		public void inDataAccessLayer() {}
		
		// serivce.* : service 패키지 내의 모든 클래스
		// service.*.*() : 모든 메서드[파라미터 제한 없음]
		@Pointcut("execution(* com.jinsu.aspectj.combinedpointcut.service.*.*(..))")
		public void businessService() {}
		
		@Pointcut("execution(* com.jinsu.aspectj.combinedpointcut.dao.*.*(..))")
		public void dataAccessOperation() {}
}