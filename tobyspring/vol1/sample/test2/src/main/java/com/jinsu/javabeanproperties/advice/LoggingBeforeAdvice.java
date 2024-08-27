package com.jinsu.javabeanproperties.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class LoggingBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        // 로그 메시지를 출력합니다.
        System.out.println("Before method: " + method.getName());
        // 추가적으로 메서드 파라미터나 타겟 객체에 대한 정보도 로그로 남길 수 있습니다.
        System.out.println("Arguments: " + (args != null ? args.length : "none"));
        System.out.println("Target: " + target.getClass().getName());
    }
}