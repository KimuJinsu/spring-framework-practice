package com.jinsu.manipulatingadvisedobjects;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.jinsu.manipulatingadvisedobjects.service.MyService;
import com.jinsu.manipulatingadvisedobjects.config.AppConfig;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myServiceProxy = (MyService) context.getBean("myServiceProxy");

        Advised advised = (Advised) myServiceProxy;

        // Initial execution with first advisor (DebugInterceptor)
        System.out.println("---- First Execution ----");
        myServiceProxy.performOperation();

        // Replace advisor with another (AnotherInterceptor)
        System.out.println("---- Replace Advisor and Execute ----");
        DefaultPointcutAdvisor myAdvisor = (DefaultPointcutAdvisor) context.getBean("myAdvisor");
        DefaultPointcutAdvisor anotherAdvisor = (DefaultPointcutAdvisor) context.getBean("anotherAdvisor");
        advised.replaceAdvisor(myAdvisor, anotherAdvisor);
        myServiceProxy.performOperation();

        // Remove advisor and execute with no interceptors
        System.out.println("---- Remove Advisor and Execute ----");
        advised.removeAdvisor(anotherAdvisor);
        myServiceProxy.performOperation();
    }
}