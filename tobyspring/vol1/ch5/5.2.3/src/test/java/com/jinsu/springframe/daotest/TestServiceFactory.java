package com.jinsu.springframe.daotest;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.dao.UserDaoJdbc;
import com.jinsu.springframe.service.UserService;

@Configuration // 이 클래스가 Spring의 Configuration 클래스로, Bean 정의를 포함하고 있음을 나타냅니다.
public class TestServiceFactory {
    
    @Bean
    public DataSource dataSource() {
        // SimpleDriverDataSource는 데이터베이스 연결을 위한 기본 DataSource 구현입니다.
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        
        // JDBC 드라이버 설정
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class); // MySQL JDBC 드라이버 클래스
        dataSource.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8"); // 데이터베이스 URL
        dataSource.setUsername("root"); // 데이터베이스 사용자명
        dataSource.setPassword("wlstnek123"); // 데이터베이스 비밀번호

        return dataSource; // 설정된 DataSource 반환
    }

    @Bean
    public UserDao userDao() {
        // UserDaoJdbc는 UserDao 인터페이스의 구현체입니다.
        UserDao userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource()); // 앞에서 정의한 DataSource를 설정
        return userDao; // 설정된 UserDao 반환
    }
    
    @Bean
    public UserService userService() {
        // UserService의 인스턴스를 생성합니다.
        UserService userService = new UserService();
        userService.setUserDao(userDao()); // 앞에서 정의한 UserDao를 설정
        userService.setDataSource(dataSource()); // 앞에서 정의한 DataSource를 설정
        return userService; // 설정된 UserService 반환
    }    
}