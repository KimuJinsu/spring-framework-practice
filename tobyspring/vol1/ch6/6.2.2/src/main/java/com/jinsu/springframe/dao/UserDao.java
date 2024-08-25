package com.jinsu.springframe.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.jinsu.springframe.domain.User;

public interface UserDao {
	
    // DataSource를 설정하는 메서드입니다. DataSource는 데이터베이스 연결을 관리하는 객체로,
    // 이 메서드를 통해 UserDao가 사용할 데이터베이스를 지정합니다.
	void setDataSource(DataSource dataSource);
	
    // 새로운 사용자를 데이터베이스에 추가하는 메서드입니다.
    // User 객체를 받아 이를 데이터베이스의 users 테이블에 삽입합니다.
	void add(User user);

    // 주어진 ID를 사용하여 데이터베이스에서 사용자를 검색하는 메서드입니다.
    // Optional<User>를 반환하여, 사용자가 존재하지 않을 경우에도 안전하게 처리할 수 있도록 합니다.
	Optional<User> get(String id);

    // 데이터베이스에 저장된 모든 사용자를 조회하여, List<User>로 반환하는 메서드입니다.
	List<User> getAll();

    // 데이터베이스의 모든 사용자 정보를 삭제하는 메서드입니다.
    // 보통 테스트 환경이나 특정 시나리오에서 전체 데이터를 초기화할 때 사용됩니다.
	void deleteAll();

    // 데이터베이스에 저장된 사용자 수를 반환하는 메서드입니다.
    // 이를 통해 현재 데이터베이스에 몇 명의 사용자가 저장되어 있는지 확인할 수 있습니다.
	int getCount();	
	
    // 데이터베이스에 저장된 사용자의 정보를 업데이트하는 메서드입니다.
    // User 객체의 ID를 기준으로 해당 사용자의 정보를 갱신합니다.
	void update(User user);

}