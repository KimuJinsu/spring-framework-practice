package com.jinsu.aspectj.declaringpointcut.service;

import static java.lang.System.out;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
// class를 컴포넌트라고 부르기도 한다.
public class TransferService {
	
	public void transfer(String account, double amount) {
		System.out.println("--------------------------------------------------------------------------------------");

		System.out.println("Transfering " + amount + 
				" to account " + account);
		System.out.println("--------------------------------------------------------------------------------------");

	}

	public void checkBalance() {
		System.out.println("--------------------------------------------------------------------------------------");

		System.out.println("check balance");
		System.out.println("--------------------------------------------------------------------------------------");

	}
}
