package com.jinsu.springframe.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import com.jinsu.springframe.domain.User;

public interface UserDao {
    
    void setDataSource(DataSource dataSource);  // 데이터베이스 연결 설정

    void add(User user);  // 사용자 추가

    Optional<User> get(String id);  // ID로 사용자 조회

    List<User> getAll();  // 모든 사용자 조회

    void deleteAll();  // 모든 사용자 삭제

    int getCount();  // 사용자 수 반환

    void update(User user);  // 사용자 정보 업데이트
}