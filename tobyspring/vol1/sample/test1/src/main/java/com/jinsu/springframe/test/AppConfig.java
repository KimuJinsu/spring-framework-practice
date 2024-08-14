package com.jinsu.springframe.test;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        // 개발 환경용 데이터소스 설정
    	SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
   	 dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
     dataSource.setUrl("jdbc:mysql://localhost:3306/sbdt_db?characterEncoding=UTF-8");
     dataSource.setUsername("root");
     dataSource.setPassword("wlstnek123");

		return dataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        // 프로덕션 환경용 데이터소스 설정
        return new HikariDataSource(); // 다른 데이터베이스 설정
    }
}