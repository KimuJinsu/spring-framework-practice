package com.jinsu.aspectj.declaringpointcut.aspect;

import static java.lang.System.out;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // 특정 메서드 이름 "transfer"를 매칭하는 포인트컷
    @Pointcut("execution(* transfer(..))")
    private void anyTransferOperation() {}

    // TransferService 클래스 내 모든 메서드를 매칭하는 포인트컷
    @Pointcut("within(com.jinsu.aspectj.declaringpointcut.service.TransferService)")
    private void withinTransferService() {}

    // 프록시 객체의 타입이 TransferService일 때 매칭하는 포인트컷
    @Pointcut("this(com.jinsu.aspectj.declaringpointcut.service.TransferService)")
    private void proxyIsTransferService() {}

    // 실제 타겟 객체의 타입이 SpecialService일 때 매칭하는 포인트컷
    @Pointcut("target(com.jinsu.aspectj.declaringpointcut.service.SpecialService)")
    private void targetIsSpecialService() {}

    // 첫 번째 인자가 String 타입인 메서드를 매칭하는 포인트컷
    @Pointcut("args(String, ..)")
    private void methodWithStringArg() {}

    // 메서드에 @Loggable 애노테이션이 붙어 있는 경우 매칭하는 포인트컷
    @Pointcut("@annotation(com.jinsu.aspectj.declaringpointcut.annotation.Loggable)")
    private void loggableMethods() {}

    // 클래스에 @SpecialComponent 애노테이션이 붙어 있는 경우 매칭하는 포인트컷
    @Pointcut("@within(com.jinsu.aspectj.declaringpointcut.annotation.SpecialComponent)")
    private void withinSpecialComponent() {}

    // 실제 객체가 @SpecialComponent 애노테이션을 가진 경우 매칭하는 포인트컷
    @Pointcut("@target(com.jinsu.aspectj.declaringpointcut.annotation.SpecialComponent)")
    private void targetHasSpecialComponent() {}

    // 메서드 인자 중 @Validated 애노테이션이 붙은 경우 매칭하는 포인트컷
    @Pointcut("@args(com.jinsu.aspectj.declaringpointcut.annotation.Validated)")
    private void methodWithValidatedArgs() {}

    // 포인트컷에 매칭될 때 실행되는 로그 어드바이스들
    // -----------------------------------------------------------------------------
    
    @Before("anyTransferOperation()")
    public void logBeforeTransfer() {
        out.println("Log Before Transfer Operation");
        out.println("--------------------------------------------------------------------------------------");
    }

    @Before("withinTransferService()")
    public void logBeforeWithinTransferService(JoinPoint joinpoint) {
        out.println("Log Before Method in TransferService");
        printJoinPointDetails(joinpoint);
    }

    @Before("proxyIsTransferService()")
    public void logWhenProxyIsTransferService(JoinPoint joinpoint) {
        out.println("Logging when Proxy is of type TransferService");
        printJoinPointDetails(joinpoint);
    }

    @Before("targetIsSpecialService()")
    public void logWhenTargetIsSpecialService(JoinPoint joinpoint) {
        out.println("Logging when Target is of type SpecialService");
        printJoinPointDetails(joinpoint);
    }

    @Before("methodWithStringArg()")
    public void logForMethodWithStringArg(JoinPoint joinpoint) {
        out.println("Logging for Method with String Argument");
        printJoinPointDetails(joinpoint);
        out.println("Arguments: " + Arrays.toString(joinpoint.getArgs()));
        out.println("--------------------------------------------------------------------------------------");
    }

    @Before("loggableMethods()")
    public void logForLoggableMethods(JoinPoint joinpoint) {
        out.println("Logging for Method annotated with @Loggable");
        printJoinPointDetails(joinpoint);
    }

    @Before("withinSpecialComponent()")
    public void logWithinSpecialComponent(JoinPoint joinpoint) {
        out.println("Logging for Methods within a @SpecialComponent Class");
        printJoinPointDetails(joinpoint);
    }

    @Before("targetHasSpecialComponent()")
    public void logWhenTargetHasSpecialComponent(JoinPoint joinpoint) {
        out.println("Logging for Target annotated with @SpecialComponent");
        printJoinPointDetails(joinpoint);
    }

    @Before("methodWithValidatedArgs()")
    public void logForMethodWithValidatedArgs(JoinPoint joinpoint) {
        out.println("Logging for Methods with Validated Arguments");
        printJoinPointDetails(joinpoint);
    }

    // -----------------------------------------------------------------------------
    
    // JoinPoint 정보 출력
    private void printJoinPointDetails(JoinPoint joinpoint) {
        out.println("Method: " + joinpoint.getSignature().getName());
        out.println("Proxy Class: " + joinpoint.getThis().getClass().getName());
        out.println("Target Class: " + joinpoint.getTarget().getClass().getName());
        out.println("--------------------------------------------------------------------------------------");
    }
}