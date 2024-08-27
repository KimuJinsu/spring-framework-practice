package com.jinsu.javabeanproperties.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jinsu.javabeanproperties.advice.LoggingBeforeAdvice;
import com.jinsu.javabeanproperties.service.SimpleService;
import com.jinsu.javabeanproperties.service.SimpleServiceImpl;

@Configuration
public class AppConfig {
	
	@Bean
	public SimpleService simpleService() {
		return new SimpleServiceImpl();
	}
	
	@Bean
	public LoggingBeforeAdvice loggingBeforeAdvice() {
		return new LoggingBeforeAdvice();
	}
	
	@Bean
	public NameMatchMethodPointcut nameMatchMethodPointcut() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("doSomething");
		return pointcut;
	}
	
	
	
	@Bean
	public DefaultPointcutAdvisor loggingAdvisor() {
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(nameMatchMethodPointcut());
		advisor.setAdvice(loggingBeforeAdvice());
		return advisor;
		
	}
	
	@Bean
	public ProxyFactoryBean proxyFactoryBean() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		
		proxyFactoryBean.setTarget(simpleService());
		
		proxyFactoryBean.setProxyTargetClass(true);
		
		proxyFactoryBean.setOptimize(false);
		
		proxyFactoryBean.setFrozen(false);
		
		proxyFactoryBean.setInterfaces(new Class<?>[] {SimpleService.class});
		
		proxyFactoryBean.setExposeProxy(true);
		
		proxyFactoryBean.setInterceptorNames("loggingAdvisor");
		
		proxyFactoryBean.setSingleton(true);
		
		return proxyFactoryBean;
		
		
	}
	
	@Bean 
	public SimpleService proxySimpleService() {
		return(SimpleService) proxyFactoryBean().getObject();
	}
}
