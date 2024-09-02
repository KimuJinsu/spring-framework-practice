package com.jinsu.aspectj.declaringadvice.model;

public class DefaultUsageTracked implements UsageTracked {

	private int useCount = 0;
	
	public void incrementUseCount() {
		useCount++;
	}

	public int getUseCount() {
		// TODO Auto-generated method stub
		return this.useCount;
	}

}