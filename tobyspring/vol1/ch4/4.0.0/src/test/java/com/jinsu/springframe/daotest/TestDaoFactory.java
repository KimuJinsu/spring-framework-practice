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
        dataSource.setUrl("jdbc:mysql://localhost/sbdt_db?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("wlstnek123");
        return dataSource;
    }

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }
}