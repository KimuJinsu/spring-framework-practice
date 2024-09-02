package com.jinsu.aspectj.combinedpointcut.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MyDao {
	public void accessData() {
		System.out.println("MyDao:accessData: Accessing data");
	}

}
