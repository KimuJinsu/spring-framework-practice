package com.jinsu.springframe.learningtest.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jinsu.springframe.learningtest.factorybean.MyCustomObject;
import com.jinsu.springframe.learningtest.factorybean.MyCustomObjectFactoryBean;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestJUnit.class})
//@DirtiesContext 
public class JUnitTest {
    
    @Autowired 
    ApplicationContext context;
    
    static Set<JUnitTest> testObjects = new HashSet<>();
    static ApplicationContext contextObject = null;
    
    @Test 
    @Order(1)
    public void test1() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
        
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }
    
    @Test 
    @Order(2)
    public void test2() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
        
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }
    @Test 
    @Order(3)
    @DirtiesContext 
    public void test3() throws IllegalStateException, Exception {
		// Bean Scope : singleton, prototype, request[Http Request]
		ConfigurableApplicationContext configAppContext = 
				(ConfigurableApplicationContext) context;
//		configAppContext.getBeanFactory().destroyBean(
//				"myCustomObject", 
//				MyCustomObject.class);

		
		// 빈으로 등록된 FactoryBean은 getBean으로 가져올 때 '&'를 붙여야 함.
		// 그렇지 않으면, FactoryBean이 생성하는 빈 객체를 리턴함!!!
		MyCustomObjectFactoryBean factoryBean =
				(MyCustomObjectFactoryBean) context.getBean("&myCustomObjectFactory");
		
//		beanFactory.registerSingleton("myCustomObject", 
//				factoryBean.getObject());
		configAppContext.getBeanFactory().registerSingleton("myCustomObject2", 
				factoryBean.getObject());
		
		MyCustomObject myObject = 
				(MyCustomObject) context.getBean("myCustomObject");
		
		MyCustomObject myObject2 = 
				(MyCustomObject) context.getBean("myCustomObject2");
		
//		System.out.println(myObject.getName());
		Assertions.assertNotNull(myObject2);
	}
    
    @Test
    @Order(4)
    public void test4() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
        
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }


    
    
    @Test
    @Order(5)
    public void test5() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
        
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }
}

