package com.jinsu.springframe.learningtest.test;

//AppConfig.java (스프링 설정 클래스)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

 @Bean
 public MyBean myBean() {
     MyBean myBean = new MyBean();
     myBean.setName("John Doe");
     myBean.setAge(30);
     return myBean;
 }
}