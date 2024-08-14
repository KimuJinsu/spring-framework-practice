package com.jinsu.springframe.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.jinsu.springframe.domain.Level;
import com.jinsu.springframe.domain.User;

public class UserDaoJdbc implements UserDao {
    
    private JdbcTemplate jdbcTemplate; // Spring의 JdbcTemplate 객체를 사용하여 데이터베이스 작업 수행
    
    // 데이터 소스를 설정하고, JdbcTemplate을 초기화합니다.
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    // User 객체를 데이터베이스의 행으로 변환하기 위한 RowMapper를 정의합니다.
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));            // 사용자 ID 설정
            user.setName(rs.getString("name"));        // 사용자 이름 설정
            user.setPassword(rs.getString("password")); // 사용자 비밀번호 설정
            user.setLevel(Level.valueOf(rs.getInt("level"))); // 사용자 레벨 설정
            user.setLogin(rs.getInt("login"));          // 사용자 로그인 수 설정
            user.setRecommend(rs.getInt("recommend"));  // 사용자 추천 수 설정
            return user;
        };
    }
    
    // 사용자를 데이터베이스에 추가합니다.
    @Override
    public void add(User user) {
        try {
            this.jdbcTemplate.update(
                "insert into users(id, name, password, level, login, recommend) " +
                "values(?,?,?,?,?,?)", 
                user.getId(), 
                user.getName(), 
                user.getPassword(), 
                user.getLevel().intValue(), // Level 객체를 정수 값으로 변환하여 저장
                user.getLogin(), 
                user.getRecommend());
        } catch (DataAccessException de) {
            System.out.println(de); // 데이터 접근 예외 발생 시 로그 출력
        }
    }

    // 특정 ID를 가진 사용자를 데이터베이스에서 조회합니다.
    @Override
    public Optional<User> get(String id) {
        String sql = "select * from users where id = ?";
        try (Stream<User> stream = 
                jdbcTemplate.queryForStream(
                        sql, 
                        userRowMapper(),
                        id)) {
            return stream.findFirst(); // 첫 번째 결과를 반환
        } catch (DataAccessException e) {
            return Optional.empty(); // 예외 발생 시 빈 Optional 반환
        }
    }

    // 모든 사용자를 데이터베이스에서 조회합니다.
    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
                userRowMapper()); // 사용자 목록을 ID로 정렬하여 반환
    }

    // 모든 사용자를 데이터베이스에서 삭제합니다.
    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users"); // 모든 사용자 삭제
    }

    // 데이터베이스에 있는 사용자 수를 반환합니다.
    @Override
    public int getCount() {
        List<Integer> result = jdbcTemplate.query(
                "select count(*) from users",
                (rs, rowNum) -> rs.getInt(1)); // 사용자 수를 조회
        
        return DataAccessUtils.singleResult(result); // 단일 결과를 반환
    }
    
    // 사용자의 정보를 업데이트합니다.
    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set "
                + "name = ?, "
                + "password = ?, "
                + "level = ?, "
                + "login = ?, " 
                + "recommend = ? "
                + "where id = ? ", 
                user.getName(), 
                user.getPassword(), 
                user.getLevel().intValue(), // Level 객체를 정수 값으로 변환하여 저장
                user.getLogin(), 
                user.getRecommend(),
                user.getId());
    }
}