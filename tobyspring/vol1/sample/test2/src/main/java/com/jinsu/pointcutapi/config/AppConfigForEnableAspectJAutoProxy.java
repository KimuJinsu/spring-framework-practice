package com.jinsu.pointcutapi.config;

import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

import com.jinsu.pointcutapi.advice.ExceptionHandlingAdvice;
import com.jinsu.pointcutapi.advice.ExecutionTimeAdvice;
import com.jinsu.pointcutapi.advice.LoggingAdvice;
import com.jinsu.pointcutapi.pointcut.CustomPointCut;
import com.jinsu.pointcutapi.service.AnotherService;
import com.jinsu.pointcutapi.service.MyService;

@Configuration
@EnableAspectJAutoProxy
public class AppConfigForEnableAspectJAutoProxy {
	

	    @Bean
	    public Pointcut customPointCut() {
	        return new CustomPointCut();
	    }

	
	    @Bean
	    public Pointcut aspectJPointcut() {
	        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	        pointcut.setExpression("execution(* com.jinsu.pointcutapi.service.AnotherService.myMethod(..))");
	        return pointcut;
	    }
	    
	    @Bean
	    public DefaultPointcutAdvisor aspectJLoggingAdvisor(
	            @Qualifier("aspectJPointcut") Pointcut pointcut) {
	        return new DefaultPointcutAdvisor(pointcut, new LoggingAdvice());
	    }

	  
	    @Bean
	    public DefaultPointcutAdvisor loggingAdvisor(
	            @Qualifier("customPointCut") Pointcut pointcut) {
	        return new DefaultPointcutAdvisor(pointcut, new LoggingAdvice());
	    }


	    @Bean
	    public DefaultPointcutAdvisor executionTimeAdvisor(
	            @Qualifier("customPointCut") Pointcut pointcut) {
	        return new DefaultPointcutAdvisor(pointcut, new ExecutionTimeAdvice());
	    }

	
	    @Bean
	    public DefaultPointcutAdvisor exceptionHandlingAdvisor(
	            @Qualifier("customPointCut") Pointcut pointcut) {
	        return new DefaultPointcutAdvisor(pointcut, new ExceptionHandlingAdvice());
	    }


	    @Bean
	    public MyService myService() {
	        return new MyService();
	    }

	 
	    @Bean
	    public AnotherService anotherService() {
	        return new AnotherService();
	    }
	}

	

