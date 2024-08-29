package com.jinsu.aspectj.declaringpointcut.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.jinsu.aspectj.declaringpointcut.service.SpecialService;
import com.jinsu.aspectj.declaringpointcut.service.TransferService;

@Configuration
@ComponentScan(basePackages="com.jinsu.aspectj.declaringpointcut")
@EnableAspectJAutoProxy
public class AppConfig {
	
	
	
	@Bean
	public SpecialService specialService() {
		return new SpecialService();
	}
	
	@Bean
	public TransferService transferService() {
		return new TransferService();
	}

}
