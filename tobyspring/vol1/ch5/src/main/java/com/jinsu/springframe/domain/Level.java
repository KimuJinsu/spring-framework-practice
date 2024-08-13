package com.jinsu.springframe.domain;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);  // 사용자 레벨 정의
    
    private final int value;  // 레벨의 정수 값
    private final Level next;  // 다음 레벨

    Level(int value, Level next) {  
        this.value = value;
        this.next = next; 
    }

    public int intValue() {
        return value;  // 레벨의 정수 값 반환
    }

    public Level nextLevel() { 
        return this.next;  // 다음 레벨 반환
    }

    public static Level valueOf(int value) {
        switch(value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default: throw new AssertionError("Unknown value: " + value);  // 유효하지 않은 값에 대해 예외 발생
        }
    }
}