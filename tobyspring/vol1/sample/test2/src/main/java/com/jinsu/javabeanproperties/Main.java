package com.jinsu.javabeanproperties;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.jinsu.javabeanproperties.service.SimpleService;
import com.jinsu.javabeanproperties.config.AppConfig;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SimpleService proxy = (SimpleService) context.getBean("proxyFactoryBean");

        proxy.doSomething();

        try {
            SimpleService currentProxy = (SimpleService) AopContext.currentProxy();
            currentProxy.doSomething();
        } catch (IllegalStateException e) {
            System.out.println("AopContext.currentProxy() 호출 실패: " + e.getMessage());
        }

        System.out.println("Proxy Class: " + proxy.getClass().getName());

        if (proxy.getClass().getName().contains("Proxy")) {
            System.out.println("JDK 프록시가 적용되었음");
        } else {
            System.out.println("CGLIB 프록시가 적용되었음");
        }
//        if (proxy instanceof SimpleService) {
//            System.out.println("CGLIB 프록시가 적용되었음");
//        } else {
//            System.out.println("JDK 프록시가 적용되었음");
//        }

        SimpleService anotherProxyInstance = (SimpleService) context.getBean("proxyFactoryBean");

        if (proxy == anotherProxyInstance) {
            System.out.println("동일한 프록시 인스턴스");
        } else {
            System.out.println("동일하지 않은 프록시 인스턴스");
        }

        try {
            ProxyFactoryBean proxyFactoryBean =
                    (ProxyFactoryBean) context.getBean("&proxyFactoryBean");
            Advised advised = (Advised) proxyFactoryBean.getObject();
            advised.addAdvice(new MethodBeforeAdvice() {

                @Override
                public void before(Method method, Object[] args, Object target) throws Throwable {
                    System.out.println("동적으로 추가될 어드바이스");
                }

            });
            
            proxy.doSomething();
        } catch (UnsupportedOperationException e) {
            System.out.println("동적으로 어드바이스 추가 실패: " + e.getMessage());
        }
    }
}