package com.jinsu.aspectj.declaringadvice.service;

import java.util.Collection;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.jinsu.aspectj.declaringadvice.annotation.AuditCode;
import com.jinsu.aspectj.declaringadvice.annotation.AuditableCode;
import com.jinsu.aspectj.declaringadvice.model.MyType;

@Service
public class SampleService implements Sample<MyType> {

	@AuditableCode(AuditCode.USER_ACTION)
	public void sampleMethod(String data) {
		System.out.println("Executing sampleMethod with data: " + data);
	}
	
	@Override
	public void sampleGenericMethod(MyType sampleGenericParam) {
		System.out.println("Executing sampleGenericMethod called with param: " + sampleGenericParam);
		
	}

	@Override
	public void sampleGenericCollectionMethod(Collection<MyType> param) {
		System.out.println("Executing sampleGenericCollectionMethod called with param: " + param);
		
	}
	
	

}