package com.jinsu.aspectj.declaringadvice.service;

import org.springframework.stereotype.Service;

import com.jinsu.aspectj.declaringadvice.annotation.Auditable;

@Service
public class MyService {
	// 아래 람다 표현식이 다수의 메서드 아규먼트로 사용된다면,,,
	// functionname -> System.out.println("function name: " + functioname); 
	@Auditable("performTask")
	public void performTask(String hello) {
		System.out.println("MyService::performTask:Performing a task ...");
		try {
			Thread.sleep(2000); // sleep..지금부터 1초 동안 cpu 사용권을 놓겠다...1초동안
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void helloWorld(@Auditable("helloworld")String hello) {
		System.out.println("good-bye:" + hello);
	}
	
	
	

}