package com.jinsu.springframe.daotest;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.dao.UserDaoJdbc;
import com.jinsu.springframe.service.DummyMailSender;
import com.jinsu.springframe.service.UserService;
import com.jinsu.springframe.service.UserServiceImpl;
import com.jinsu.springframe.service.UserServiceTx;


@Configuration
public class TestServiceFactory {
    // DataSource 빈 생성: MySQL 데이터베이스 연결을 위한 설정
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class); // MySQL JDBC 드라이버 클래스 지정
        dataSource.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8"); // DB URL 설정
        dataSource.setUsername("root"); // DB 사용자명
        dataSource.setPassword("wlstnek123"); // DB 비밀번호
        return dataSource;
    }

    // UserDaoJdbc 빈 생성: UserDao 인터페이스의 JDBC 구현체 생성
    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.setDataSource(dataSource()); // DataSource 설정
        return userDaoJdbc;
    }

    // UserServiceImpl 빈 생성: UserService 인터페이스의 구현체를 생성
    @Bean("userServiceImplement")
    @Qualifier("userService1")
    public UserService userServiceImpl() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao()); // UserDao 설정
        userServiceImpl.setMailSender(mailSender()); // MailSender 설정
        return userServiceImpl;
    }

    // UserServiceTx 빈 생성: 트랜잭션 처리를 위한 UserServiceTx 클래스 생성
    @Bean("userServiceTranasction")
    @Qualifier("userService2")
    public UserService userServiceTx() {
        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setTransactionManager(transactionManager()); // 트랜잭션 관리자 설정
        userServiceTx.setUserService(userServiceImpl()); // 실제 비즈니스 로직 처리 객체 설정
        return userServiceTx;
    }    

    // DummyMailSender 빈 생성: 실제 메일 전송을 하지 않는 더미 메일 전송기
    @Bean
    public DummyMailSender mailSender() {
        DummyMailSender dummyMailSender = new DummyMailSender();        
        return dummyMailSender;
    }
    
    // Properties 빈 생성: 메일 전송 관련 속성 설정
    @Bean
    public Properties properites() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
    }

    // DataSourceTransactionManager 빈 생성: 트랜잭션 관리
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }    
}