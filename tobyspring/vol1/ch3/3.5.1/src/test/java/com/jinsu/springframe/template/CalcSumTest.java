package com.jinsu.springframe.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jinsu.springframe.template.Calculator;

public class CalcSumTest {
	Calculator calculator;
	String numFilepath;
	
	@BeforeAll public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("/numbers.txt").getPath();
	}
	
	@Test public void sumOfNumbers() throws IOException {
		assertEquals(calculator.calcSum(this.numFilepath), 10);
	}
	
	@Test public void multiplyOfNumbers() throws IOException {
		assertEquals(calculator.calcMultiply(this.numFilepath), 24);
	}
	
	@Test public void concatenateStrings() throws IOException {
		assertEquals(calculator.concatenate(this.numFilepath), "1234");
	}

}