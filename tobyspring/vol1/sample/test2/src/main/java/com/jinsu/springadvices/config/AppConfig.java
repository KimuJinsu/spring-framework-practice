package com.jinsu.springadvices.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jinsu.springadvices.advices.CountingAfterReturningAdvice;
import com.jinsu.springadvices.advices.CountingBeforeAdvice;
import com.jinsu.springadvices.advices.DebugInterceptor;
import com.jinsu.springadvices.advices.SimpleThrowsAdivice;
import com.jinsu.springadvices.service.SimpleService;

@Configuration
public class AppConfig {
	
	@Bean
	public SimpleService simpleService() {
		return new SimpleService();
	}
	
	@Bean
	public DebugInterceptor debugInterceptor() {
		return new DebugInterceptor();
		
	}
	
	@Bean
	public CountingBeforeAdvice countingBeforeAdvice() {
		return new CountingBeforeAdvice();
		
	}
	@Bean 
	public CountingAfterReturningAdvice countingAfterReturningAdvice() {
		return new CountingAfterReturningAdvice();
	}
	
	@Bean
	public SimpleThrowsAdivice simpleThrowsAdivice() {
		return new SimpleThrowsAdivice();
	}
	
	@Bean
	public ProxyFactoryBean proxyFactoryBean() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(simpleService());
		
		proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(debugInterceptor()));
		proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(countingAfterReturningAdvice()));
		proxyFactoryBean.setProxyTargetClass(true);
		proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(countingBeforeAdvice()));
		proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(simpleThrowsAdivice()));

		return proxyFactoryBean;

	}
}
