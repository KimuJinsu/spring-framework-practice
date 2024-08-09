package com.jinsu.springframe.learningtest.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jinsu.springframe.learningtest.factorybean.AppConfig;
import com.jinsu.springframe.learningtest.factorybean.MyCustomObject;
import com.jinsu.springframe.learningtest.factorybean.MyCustomObjectFactoryBean;

public class Main {

    public static void main(String[] args) {
        // AnnotationConfigApplicationContext를 사용하여 애플리케이션 컨텍스트 생성
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // 애플리케이션 컨텍스트에서 MyBean 객체 가져오기
        MyBean myBean = context.getBean(MyBean.class);
        
        // MyBean 객체의 속성 값 출력
        System.out.println("Name: " + myBean.getName());
        System.out.println("Age: " + myBean.getAge());
    }
}

//AnnotationConfigApplicationContext context = 
//new AnnotationConfigApplicationContext(AppConfig.class);


//public class Program {
//
//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		AnnotationConfigApplicationContext context = 
//				new AnnotationConfigApplicationContext(AppConfig.class);
//		
////		ConfigurableListableBeanFactory beanFactory =
////				context.getBeanFactory();
////		
////		beanFactory.destroyBean(context.getBean("myCustomObject", MyCustomObject.class));
////		
//		
//		// Bean Scope : singleton, prototype, request[Http Request]
//		ConfigurableApplicationContext configAppContext = 
//				(ConfigurableApplicationContext) context;
////		configAppContext.getBeanFactory().destroyBean(
////				"myCustomObject", 
////				MyCustomObject.class);
//
//		
//		// 빈으로 등록된 FactoryBean은 getBean으로 가져올 때 '&'를 붙여야 함.
//		// 그렇지 않으면, FactoryBean이 생성하는 빈 객체를 리턴함!!!
//		MyCustomObjectFactoryBean factoryBean =
//				(MyCustomObjectFactoryBean) context.getBean("&myCustomObjectFactory");
//		
////		beanFactory.registerSingleton("myCustomObject", 
////				factoryBean.getObject());
//		configAppContext.getBeanFactory().registerSingleton("myCustomObject2", 
//				factoryBean.getObject());
//		
//		MyCustomObject myObject = 
//				(MyCustomObject) context.getBean("myCustomObject");
//		
//		MyCustomObject myObject2 = 
//				(MyCustomObject) context.getBean("myCustomObject2");
//		
//		System.out.println(myObject.getName());
//	}
//
//}