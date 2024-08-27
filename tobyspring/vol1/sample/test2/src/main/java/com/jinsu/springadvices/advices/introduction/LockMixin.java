package com.jinsu.springadvices.advices.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

// Delegate : delegate는 도입될 인터페이스[Lockable]의 메서드를 실제로 구
public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {

	private boolean lcoked;
	
	@Override
	public void lock() {
		this.lcoked = true;

	}

	@Override
	public void unlock() {
		this.lcoked = false;

	}

	@Override
	public boolean locked() {
		return this.lcoked;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (locked() && invocation.getMethod().getName().startsWith("set")) {
			throw new LockedException();
			
		}
		
		return super.invoke(invocation);
	}
}
