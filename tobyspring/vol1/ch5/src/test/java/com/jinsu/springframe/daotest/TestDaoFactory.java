package com.jinsu.springframe.daotest;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import com.jinsu.springframe.dao.UserDao;
import com.jinsu.springframe.dao.UserDaoJdbc;

@Configuration
public class TestDaoFactory {
    
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("wlstnek123");

        return dataSource;  // 데이터베이스 연결 설정
    }

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDaoJdbc();  // UserDaoJdbc 인스턴스 생성
        userDao.setDataSource(dataSource());  // 데이터베이스 연결 설정
        return userDao;  // UserDao 반환
    }
}