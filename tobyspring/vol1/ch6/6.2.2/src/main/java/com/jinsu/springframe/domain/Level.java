package com.jinsu.springframe.domain;

// Enum 타입으로 사용자 레벨을 정의하는 클래스입니다. 
// 각 레벨은 숫자 값과 다음 레벨을 가지며, 레벨 간의 순서를 정의합니다.
public enum Level {
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);	 
	
	// 레벨에 대한 숫자 값입니다.
	private final int value;
	
	// 다음 단계의 레벨을 나타냅니다.
	private final Level next; 
	
	// 생성자입니다. 각 열거형 상수는 value와 next를 인자로 받아 초기화됩니다.
	Level(int value, Level next) {  
		this.value = value;
		this.next = next; 
	}
	
	// 현재 레벨의 숫자 값을 반환합니다.
	public int intValue() {
		return value;
	}
	
	// 다음 레벨을 반환합니다. null일 경우 더 높은 레벨이 없음을 의미합니다.
	public Level nextLevel() { 
		return this.next;
	}
	
	// 숫자 값을 기반으로 레벨을 반환하는 정적 메서드입니다.
	// 전달된 값에 따라 적절한 레벨을 반환하고, 값이 잘못된 경우 AssertionError를 발생시킵니다.
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return BASIC;
		case 2: return SILVER;
		case 3: return GOLD;
		default: throw new AssertionError("Unknown value: " + value);
		}
	}
}