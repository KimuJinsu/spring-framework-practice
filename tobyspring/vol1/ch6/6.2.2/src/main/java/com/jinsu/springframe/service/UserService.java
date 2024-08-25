package com.jinsu.springframe.service;

import com.jinsu.springframe.domain.User;

// UserService 인터페이스는 사용자 관련 서비스를 정의하는 인터페이스입니다.
// 이 인터페이스는 사용자 추가 및 사용자 레벨 업그레이드와 같은 주요 기능을 제공합니다.
public interface UserService {
    
    // 사용자(User) 객체를 데이터베이스에 추가하는 메서드입니다.
    void add(User user);

    // 사용자 레벨을 업그레이드하는 메서드입니다.
    // 이 메서드는 데이터베이스에 저장된 사용자 목록을 가져와서,
    // 특정 조건을 만족하는 사용자들의 레벨을 업그레이드합니다.
    void upgradeLevels();
}