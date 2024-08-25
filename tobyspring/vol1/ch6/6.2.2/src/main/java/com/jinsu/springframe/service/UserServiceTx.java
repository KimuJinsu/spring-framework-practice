package com.jinsu.springframe.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jinsu.springframe.domain.User;

// UserServiceTx는 트랜잭션 관리 기능을 제공하는 데코레이터 클래스입니다.
// 내부적으로 실제 비즈니스 로직을 처리하는 UserService 인터페이스를 구현하는 객체를 감싸며,
// 트랜잭션 시작, 커밋, 롤백을 관리합니다.
public class UserServiceTx implements UserService {
    // 트랜잭션 관리자를 주입받기 위한 필드
    private PlatformTransactionManager transactionManager;
    // 실제 비즈니스 로직을 처리하는 UserService 인터페이스 타입의 필드
    private UserService userService;

    // UserService 구현체를 주입받기 위한 setter 메서드
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    // 주입받은 UserService 구현체를 반환하는 getter 메서드
    public UserService getUserService() {
        return this.userService;
    }

    // 트랜잭션 관리자를 주입받기 위한 setter 메서드
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 주입받은 트랜잭션 관리자를 반환하는 getter 메서드
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    // 레벨 업그레이드 메서드
    public void upgradeLevels() {
        // 트랜잭션을 시작하는 부분, 기본 트랜잭션 정의를 사용
        TransactionStatus status = 
            this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 실제 비즈니스 로직 호출
            this.userService.upgradeLevels();
            // 성공 시 트랜잭션 커밋
            this.transactionManager.commit(status);
        } catch (RuntimeException e) {
            // 예외 발생 시 트랜잭션 롤백
            this.transactionManager.rollback(status);
            throw e;  // 예외를 다시 던져 호출자에게 알림
        }
    }

    // 사용자 추가 메서드
    public void add(User user) {
        // 트랜잭션 관리 없이 단순히 주입받은 userService의 add 메서드를 호출
        this.userService.add(user);
    }
}