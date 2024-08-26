package com.jinsu.pointcutapi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.jinsu.pointcutapi.config.AppConfig;
import com.jinsu.pointcutapi.config.AppConfigForEnableAspectJAutoProxy;
import com.jinsu.pointcutapi.service.AnotherService;
import com.jinsu.pointcutapi.service.MyService;

public class Main {

    public static void execAppConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MyService myService = (MyService) context.getBean("myServiceProxy");
        AnotherService anotherService = (AnotherService) context.getBean("anotherServiceProxy");

        myService.myMethod();
        myService.anotherMethod("test");

        anotherService.differentMethod(123);

        try {
            myService.methodWithExecption();
        } catch (Exception e) {
            System.out.println("Exception handled in main");
        }
        context.close();
    }

    public static void execAppConfigForEnableAspectJAutoProxy() {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigForEnableAspectJAutoProxy.class);

        MyService myService = (MyService) context.getBean("myService");
        AnotherService anotherService = (AnotherService) context.getBean("anotherService");

        myService.myMethod();
        myService.anotherMethod("test");

        anotherService.differentMethod(123);

        try {
            myService.methodWithExecption();
        } catch (Exception e) {
            System.out.println("Exception handled in main");
        }
        context.close();
    }

    public static void main(String[] args) {
        execAppConfigForEnableAspectJAutoProxy();
    }
}