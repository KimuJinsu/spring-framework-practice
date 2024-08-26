package com.jinsu.conveniencepointcut;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jinsu.conveniencepointcut.config.AppConfig;
import com.jinsu.conveniencepointcut.service.MyService;
import com.jinsu.conveniencepointcut.service.MySpecialService;

public class Main {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		
		MyService myService = (MyService) context.getBean("myService");
		myService.performOperation();
		

		MySpecialService mySpecialService = (MySpecialService) context.getBean("mySpecialService");
		mySpecialService.performSpecialOperation();
		
		context.close();
		}
}