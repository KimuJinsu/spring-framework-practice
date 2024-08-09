package com.jinsu.springframe.learningtest.junit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jinsu.springframe.learningtest.factorybean.MyCustomObject;
import com.jinsu.springframe.learningtest.factorybean.MyCustomObjectFactoryBean;

@Configuration
public class TestJUnit {
	
	@Bean
	public MyCustomObjectFactoryBean myCustomObjectFactory() {
		MyCustomObjectFactoryBean factoryBean = new MyCustomObjectFactoryBean();
		return factoryBean;
	}
	
	@Bean
	public MyCustomObject myCustomObject() throws Exception {
		return myCustomObjectFactory().getObject();
	}

}


