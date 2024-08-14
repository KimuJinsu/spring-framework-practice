package com.jinsu.springframe.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import com.jinsu.springframe.domain.User;
public interface UserDao {
    
    // 데이터 소스를 설정합니다. 이는 데이터베이스 연결을 관리하는 객체입니다.
    void setDataSource(DataSource dataSource);
    
    // 사용자 정보를 데이터베이스에 추가합니다.
    void add(User user);

    // 특정 ID를 가진 사용자를 데이터베이스에서 조회하여 반환합니다.
    Optional<User> get(String id);

    // 데이터베이스에 있는 모든 사용자 정보를 조회하여 리스트로 반환합니다.
    List<User> getAll();

    // 데이터베이스에서 모든 사용자 정보를 삭제합니다.
    void deleteAll();

    // 데이터베이스에 있는 사용자 수를 반환합니다.
    int getCount();    
    
    // 특정 사용자의 정보를 데이터베이스에서 업데이트합니다.
    void update(User user);
}