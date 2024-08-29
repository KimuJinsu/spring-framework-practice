package com.jinsu.manipulatingadvisedobjects.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jinsu.manipulatingadvisedobjects.advice.AnotherInterceptor;
import com.jinsu.manipulatingadvisedobjects.advice.DebugInterceptor;
import com.jinsu.manipulatingadvisedobjects.advice.FirstInterceptor;
import com.jinsu.manipulatingadvisedobjects.pointcut.MySpecialPointcut;
import com.jinsu.manipulatingadvisedobjects.service.MyService;
import com.jinsu.manipulatingadvisedobjects.service.MyServiceImpl;

@Configuration
public class AppConfig {

    @Bean 
    public MyService myService() {
        return new MyServiceImpl();
    }

    @Bean
    public DebugInterceptor debugInterceptor() {
        return new DebugInterceptor();
    }

    @Bean
    public FirstInterceptor firstInterceptor() {
        return new FirstInterceptor();
    }

    @Bean
    public AnotherInterceptor anotherInterceptor() {
        return new AnotherInterceptor();
    }

    @Bean
    public MySpecialPointcut mySpecialPointcut() {
        return new MySpecialPointcut();
    }

    @Bean
    public DefaultPointcutAdvisor myAdvisor(MySpecialPointcut mySpecialPointcut,
                                            DebugInterceptor debugInterceptor ) {
        return new DefaultPointcutAdvisor(mySpecialPointcut, debugInterceptor);
    }

    @Bean
    public DefaultPointcutAdvisor anotherAdvisor(MySpecialPointcut mySpecialPointcut,
                                                 AnotherInterceptor anotherInterceptor) {
        return new DefaultPointcutAdvisor(mySpecialPointcut, anotherInterceptor);
    }

    @Bean
    public ProxyFactoryBean myServiceProxy(MyService myService) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(myService);
        
        // Advisor 추가 (인터셉터 순서에 유의)
        proxyFactoryBean.addAdvisor(myAdvisor(mySpecialPointcut(), debugInterceptor()));
        proxyFactoryBean.addAdvisor(anotherAdvisor(mySpecialPointcut(), anotherInterceptor()));
        
        proxyFactoryBean.setProxyTargetClass(true); // CGLIB 프록시 사용
        return proxyFactoryBean; // 프록시 객체 반환
    }
}