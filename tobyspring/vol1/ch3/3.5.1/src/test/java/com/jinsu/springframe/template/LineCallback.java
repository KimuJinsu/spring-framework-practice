package com.jinsu.springframe.template;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}