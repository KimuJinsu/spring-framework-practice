package com.jinsu.usingautoproxyfacility.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.jinsu.usingautoproxyfacility.bean.BusinessObject1;
import com.jinsu.usingautoproxyfacility.bean.BusinessObject2;
import com.jinsu.usingautoproxyfacility.bean.MyBean;

@Configuration
@EnableTransactionManagement
public class AppConfig {
	
	@Bean
	public SimpleTraceInterceptor myInterceptor() {
		return new SimpleTraceInterceptor();
	}
	
	@Bean
	public MyBean jdkMyBean() {
		return new MyBean();
	}
	

	
	@Bean
    public  static BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setBeanNames("jdk*", "onlyBean");
        creator.setInterceptorNames("myInterceptor");
        return creator;
    }
	@Bean 
	public MyBean onlyBean() {
		return new MyBean();
	}
	
	 @Bean
	    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
	        return new DefaultAdvisorAutoProxyCreator();
	    }
	 
	 @Bean
	    public TransactionAttributeSourceAdvisor transactionAdvisor(TransactionInterceptor transactionInterceptor) {
	        TransactionAttributeSourceAdvisor advisor = new TransactionAttributeSourceAdvisor();
	        advisor.setTransactionInterceptor(transactionInterceptor);
	        return advisor;
	    }
	 
	 @Bean
	    public BusinessObject1 businessObject1() {
	        return new BusinessObject1();
	    }

	    @Bean
	    public BusinessObject2 businessObject2() {
	        return new BusinessObject2();
	    }

	

}
