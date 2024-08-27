package com.jinsu.springadvices.advices.introduction;

public class LockedException extends RuntimeException {
	
	public LockedException() {
		super("Other is locked. No modification are allowed");
	}

}
