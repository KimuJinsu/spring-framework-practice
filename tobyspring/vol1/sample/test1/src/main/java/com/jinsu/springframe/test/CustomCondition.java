package com.jinsu.springframe.test;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CustomCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 예를 들어 특정 시스템 속성이 존재하는 경우에만 true 반환
        String expectedProperty = "my.custom.property";
        String propertyValue = context.getEnvironment().getProperty(expectedProperty);
        return propertyValue != null && propertyValue.equals("enabled");
    }
}