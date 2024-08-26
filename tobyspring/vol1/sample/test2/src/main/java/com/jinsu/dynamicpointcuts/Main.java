package com.jinsu.dynamicpointcuts;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        TaskCaller caller = context.getBean(TaskCaller.class);
        caller.callTask();
        
        // 정확한 타입으로 빈 가져오기
        SimpleService proxyService = context.getBean("proxyFactoryBean", SimpleService.class);
        proxyService.performTask();
        
        context.close();
    }
}