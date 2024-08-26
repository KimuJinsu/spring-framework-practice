package com.jinsu.dynamicpointcuts;

public class SimpleService {
	
	public void performTask() {
	System.out.println("performTask: Performing a task");
	internalTask();
	}
	
	public void internalTask() {
		System.out.println("internalTask: Performing internal task");
	}
}
