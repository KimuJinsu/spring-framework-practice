package com.jinsu.aspectj.declaringpointcut.service;

import static java.lang.System.out;

import com.jinsu.aspectj.declaringpointcut.annotation.Loggable;
import com.jinsu.aspectj.declaringpointcut.annotation.SpecialComponent;
import com.jinsu.aspectj.declaringpointcut.annotation.Validated;

@SpecialComponent
public class SpecialService {
	
	@Loggable
	public void specialOperation(String operation) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Performing special operation: " + operation);
		System.out.println("--------------------------------------------------------------------------------------");

	}

	public void anotherSpecialOperation(@Validated String parameter) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Performing another special operation: " + parameter);
		System.out.println("--------------------------------------------------------------------------------------");


		
	}
}
