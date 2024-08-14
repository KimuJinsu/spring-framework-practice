package com.jinsu.springframe.domain;

public enum Level {
    // 각 레벨을 정의하고, 각 레벨의 값을 설정합니다.
    GOLD(3, null),       // GOLD 레벨, 다음 레벨이 없음
    SILVER(2, GOLD),     // SILVER 레벨, 다음 레벨은 GOLD
    BASIC(1, SILVER);    // BASIC 레벨, 다음 레벨은 SILVER
    
    private final int value;      // 레벨의 정수 값
    private final Level next;     // 다음 레벨
    
    // Level 생성자: 각 레벨의 정수 값과 다음 레벨을 설정합니다.
    Level(int value, Level next) {  
        this.value = value;
        this.next = next; 
    }
    
    // 레벨의 정수 값을 반환하는 메서드
    public int intValue() {
        return value;
    }
    
    // 현재 레벨의 다음 레벨을 반환하는 메서드
    public Level nextLevel() { 
        return this.next;
    }
    
    // 주어진 정수 값에 해당하는 레벨을 반환하는 정적 메서드
    public static Level valueOf(int value) {
        switch(value) {
        case 1: return BASIC;   // 값이 1이면 BASIC 레벨 반환
        case 2: return SILVER;  // 값이 2이면 SILVER 레벨 반환
        case 3: return GOLD;    // 값이 3이면 GOLD 레벨 반환
        default: throw new AssertionError("Unknown value: " + value); // 정의되지 않은 값에 대해 예외 발생
        }
    }
}