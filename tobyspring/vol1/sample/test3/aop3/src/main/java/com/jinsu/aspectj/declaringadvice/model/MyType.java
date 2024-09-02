package com.jinsu.aspectj.declaringadvice.model;

public class MyType {
	
	private String name;
	
	public MyType(String name) {this.name = name;}
	
	@Override
	public String toString() { return "MyType{name='" + name + "'}";}

}