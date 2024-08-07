package com.jinsu.springframe.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
	@Bean
	public UserDao userDao() {
		// dependency injection : Constructor Argument 방식
		// UserDao에게는 connectionMaker 구체가 필요하다.
		return new UserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		//// dependency injection : Constructor Argument 방식
		return new CountingConnectionMaker(realConnectionMaker());
	}

	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new MConnectionMaker();
	}
}