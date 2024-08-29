package com.jinsu.usingautoproxyfacility;

import org.springframework.context.ApplicationContext;
import static java.lang.System.out;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cglib.proxy.Proxy;

import com.jinsu.usingautoproxyfacility.bean.MyBean;
import com.jinsu.usingautoproxyfacility.config.AppConfig;
import com.jinsu.usingautoproxyfacility.service.BusinessService;

public class Main {
	
	public static void printProxyInfo(Object bean) {
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
		out.println("Bean Class: " + bean.getClass().getName());
		out.println("Is JDK Dynamic Proxy: " + Proxy.isProxyClass(bean.getClass()));
		out.println("Is CGRIB Proxy: " + bean.getClass().getName().contains("$$"));
		out.println("Target Class: " + targetClass.getName());
		out.println("--------------------------------------------------------------------");
	}
	

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		MyBean jdkMyBean = context.getBean("jdkMyBean", MyBean.class);
															// Bean이름과 타입이름을 써주면 좋다.
		
		printProxyInfo(jdkMyBean);
		
		MyBean onlyBean = context.getBean("onlyBean", MyBean.class);
		// Bean이름과 타입이름을 써주면 좋다.

		printProxyInfo(onlyBean);
		
		BusinessService buzz1 = context.getBean("businessObject1", BusinessService.class);
		printProxyInfo(buzz1);
		BusinessService buzz2 = context.getBean("businessObject1", BusinessService.class);
		printProxyInfo(buzz2);
	
		
		
		
	}

}
