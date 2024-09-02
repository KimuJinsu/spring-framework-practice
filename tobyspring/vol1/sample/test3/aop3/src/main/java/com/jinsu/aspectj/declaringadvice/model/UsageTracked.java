package com.jinsu.aspectj.declaringadvice.model;

public interface UsageTracked {
	
	void incrementUseCount();
	
	int getUseCount();

}