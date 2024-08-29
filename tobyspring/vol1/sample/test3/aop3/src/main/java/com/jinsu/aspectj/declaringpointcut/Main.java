package com.jinsu.aspectj.declaringpointcut;

import com.jinsu.aspectj.declaringpointcut.config.AppConfig;
import com.jinsu.aspectj.declaringpointcut.service.SpecialService;
import com.jinsu.aspectj.declaringpointcut.service.TransferService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        // Spring ApplicationContext 생성 및 AppConfig 설정
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 필요한 Bean 가져오기
        TransferService transferService = context.getBean(TransferService.class);
        SpecialService specialService = context.getBean(SpecialService.class);

        // 서비스 메서드 실행
        transferService.transfer("12345", 10000);
        transferService.checkBalance();
        specialService.specialOperation("Hi");
        specialService.anotherSpecialOperation("GoodBye");
    }
}
