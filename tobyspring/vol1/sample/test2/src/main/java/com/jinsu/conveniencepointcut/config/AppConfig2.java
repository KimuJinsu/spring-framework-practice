package com.jinsu.conveniencepointcut.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jinsu.conveniencepointcut.advice.SimpleAdvice;
import com.jinsu.conveniencepointcut.service.SimpleService;

@Configuration
public class AppConfig2 {
	
	@Bean
	public SimpleService simpleService () {
		return new SimpleService ();
		
	}
	
	@Bean
	public SimpleAdvice simpleAdvice () {
		return new SimpleAdvice();
		
	}
	
	@Bean
	public JdkRegexpMethodPointcut jdkRegexMethodPointcut() {
		
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		
		pointcut.setPattern(".*get.*");
		return pointcut;
		
	}
	
	@Bean
	public DefaultPointcutAdvisor defaultPointcytAdvisor() {
		return new DefaultPointcutAdvisor(jdkRegexMethodPointcut(),
				simpleAdvice());
	}
	
	@Bean 
	public RegexpMethodPointcutAdvisor setterAndAbsquatulateAdvisor() {
		RegexpMethodPointcutAdvisor advisor = 
				new RegexpMethodPointcutAdvisor();
		advisor.setAdvice(simpleAdvice());
		advisor.setPatterns(".*set.*",".*absquatulate.*");
		return advisor;
	}
	
	@Bean
	public ProxyFactoryBean proxyFactoryBean() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		
		proxyFactoryBean.setTarget(simpleService());
		proxyFactoryBean.addAdvisor(setterAndAbsquatulateAdvisor());
		proxyFactoryBean.addAdvisor(defaultPointcytAdvisor());

		return proxyFactoryBean;
	}

}
