package com.jinsu.conveniencepointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jinsu.conveniencepointcut.config.AppConfig2;
import com.jinsu.conveniencepointcut.service.SimpleService;

public class Program {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(AppConfig2.class);
		
		SimpleService service = (SimpleService) context.getBean("proxyFactoryBean");
		
		service.setName("John");
		service.getName();
		service.absquatulate();
		service.performTask();
	}

}
