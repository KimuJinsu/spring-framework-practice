package com.jinsu.springadvices;


import com.jinsu.springadvices.config.AppConfig;
import com.jinsu.springadvices.service.SimpleService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		SimpleService service = (SimpleService)context.getBean("proxyFactoryBean");
		
		service.performTask();
		
		String greeting = service.returnGreeting("Me");
		System.out.println("Grreeting: " + greeting);
		
		try {
			service.throwException();
			
		}catch (Exception e) {
			System.out.println("Exception caught in main:" + e.getMessage());;
		}
	}
		
}
