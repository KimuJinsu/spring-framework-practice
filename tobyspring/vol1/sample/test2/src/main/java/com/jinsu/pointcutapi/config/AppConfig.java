package com.jinsu.pointcutapi.config;

import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.jinsu.pointcutapi.advice.ExceptionHandlingAdvice;
import com.jinsu.pointcutapi.advice.LoggingAdvice;
import com.jinsu.pointcutapi.advice.ExecutionTimeAdvice;
import com.jinsu.pointcutapi.pointcut.CustomPointCut;
import com.jinsu.pointcutapi.service.AnotherService;
import com.jinsu.pointcutapi.service.MyService;

@Configuration
public class AppConfig {


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
    public ProxyFactoryBean myServiceProxy(
            MyService myservice,
            @Qualifier("loggingAdvisor") DefaultPointcutAdvisor loggingAdvisor,
            @Qualifier("executionTimeAdvisor") DefaultPointcutAdvisor executionTimeAdvisor,
            @Qualifier("exceptionHandlingAdvisor") DefaultPointcutAdvisor exceptionHandlingAdvisor) {

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(myservice);
        proxyFactoryBean.setInterceptorNames(
                "loggingAdvisor",
                "executionTimeAdvisor",
                "exceptionHandlingAdvisor");
        return proxyFactoryBean;
    }

    @Bean
    public ProxyFactoryBean anotherServiceProxy(
            AnotherService anotherService,
            @Qualifier("loggingAdvisor") DefaultPointcutAdvisor loggingAdvisor,
            @Qualifier("executionTimeAdvisor") DefaultPointcutAdvisor executionTimeAdvisor,
            @Qualifier("exceptionHandlingAdvisor") DefaultPointcutAdvisor exceptionHandlingAdvisor) {

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(anotherService);
        proxyFactoryBean.setInterceptorNames(
                "loggingAdvisor",
                "executionTimeAdvisor",
                "exceptionHandlingAdvisor");
        return proxyFactoryBean;
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