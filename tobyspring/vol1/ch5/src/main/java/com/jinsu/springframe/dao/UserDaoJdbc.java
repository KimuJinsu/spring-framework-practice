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
    
    private JdbcTemplate jdbcTemplate;  // Spring의 JdbcTemplate을 사용하여 데이터베이스 작업 수행
    
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);  // 데이터베이스 연결 설정
    }
    
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));  // 데이터베이스 값으로부터 Level 객체 생성
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        };
    }

    @Override
    public void add(User user) {
        try {
            this.jdbcTemplate.update(
                    "insert into users(id, name, password, level, login, recommend) " +
                            "values(?,?,?,?,?,?)", 
                            user.getId(), 
                            user.getName(), 
                            user.getPassword(), 
                            user.getLevel().intValue(),  // Level을 정수 값으로 변환하여 저장
                            user.getLogin(), 
                            user.getRecommend());
        } catch (DataAccessException de) {
            System.out.println(de);  // 예외 발생 시 콘솔에 출력
        }
    }

    @Override
    public Optional<User> get(String id) {
        String sql = "select * from users where id = ?";
        try (Stream<User> stream = 
                jdbcTemplate.queryForStream(
                        sql, 
                        userRowMapper(),
                        id)) {
            return stream.findFirst();  // 첫 번째 사용자만 반환
        } catch (DataAccessException e) {
            return Optional.empty();  // 예외 발생 시 빈 Optional 반환
        }
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
                userRowMapper());  // 모든 사용자 조회
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");  // 모든 사용자 삭제
    }

    @Override
    public int getCount() {
        List<Integer> result = jdbcTemplate.query(
                "select count(*) from users",
                (rs, rowNum) -> rs.getInt(1));  // 사용자 수 조회
        
        return DataAccessUtils.singleResult(result);  // 결과 리스트에서 단일 결과 반환
    }
    
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
                user.getLevel().intValue(),  // Level을 정수 값으로 변환하여 업데이트
                user.getLogin(), 
                user.getRecommend(),
                user.getId());
    }
}