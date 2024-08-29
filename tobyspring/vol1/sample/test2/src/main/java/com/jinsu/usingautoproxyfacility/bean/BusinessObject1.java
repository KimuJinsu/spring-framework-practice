package com.jinsu.usingautoproxyfacility.bean;

import org.springframework.transaction.annotation.Transactional;

import com.jinsu.usingautoproxyfacility.service.BusinessService;

public class BusinessObject1 implements BusinessService{

	@Transactional
	public void process() {
		System.out.println("Processing in BusinessObject1");
	}

}
