package com.jinsu.pointcutapi.service;

import static java.lang.System.out;

public class MyService {
	
	@CustomAnnotation
	public void myMethod() {
		
		out.println("Executing myMethod");
		
	}
	@CustomAnnotation

	public void anotherMethod(String arg) {
		out.println("Executing anotherMethod with arg: " + arg);
	}
	
	public void methodWithExecption() throws Exception {
		throw new Exception("Exeption in methodWithExecption");
	}

}
