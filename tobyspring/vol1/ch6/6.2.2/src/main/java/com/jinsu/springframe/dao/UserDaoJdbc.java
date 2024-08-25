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

// UserDao 인터페이스를 구현한 클래스입니다. 이 클래스는 JDBC를 사용하여 
// 데이터베이스에 접근하고, 사용자 정보를 관리합니다.
public class UserDaoJdbc implements UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	// DataSource를 설정하는 메서드입니다. DataSource는 데이터베이스 연결을 관리하는 객체로,
    // 이 메서드를 통해 jdbcTemplate을 초기화합니다.
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// RowMapper를 정의하는 메서드입니다. RowMapper는 ResultSet에서 데이터를 추출하여
    // User 객체로 매핑하는 역할을 합니다.
	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		};
	}

	// 사용자 정보를 데이터베이스에 추가하는 메서드입니다.
    // 사용자 객체의 정보를 users 테이블에 삽입합니다.
	@Override
	public void add(User user) {
		try {
			this.jdbcTemplate.update(
					"insert into users(id, name, password, email, level, login, recommend) " +
							"values(?,?,?,?,?,?,?)", 
							user.getId(), 
							user.getName(), 
							user.getPassword(), 
							user.getEmail(),
							user.getLevel().intValue(), 
							user.getLogin(), 
							user.getRecommend());
		} catch (DataAccessException de) {
			System.out.println(de);			
		} 
	}

	// 주어진 ID로 사용자를 검색하여 Optional<User>로 반환하는 메서드입니다.
	@Override
	public Optional<User> get(String id) {
		String sql = "select * from users where id = ?";
		try (Stream<User> stream = 
				jdbcTemplate.queryForStream(
						sql, 
						userRowMapper(),
						id)) {
			return stream.findFirst();
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	// 데이터베이스에 저장된 모든 사용자 정보를 List<User>로 반환하는 메서드입니다.
	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",
				userRowMapper());
	}

	// 데이터베이스의 모든 사용자 정보를 삭제하는 메서드입니다.
	@Override
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	// 데이터베이스에 저장된 사용자 수를 반환하는 메서드입니다.
	@Override
	public int getCount() {
		List<Integer> result = jdbcTemplate.query(
				"select count(*) from users",
				(rs, rowNum) -> rs.getInt(1));
		
		return DataAccessUtils.singleResult(result);
	}
	
	// 주어진 사용자의 정보를 업데이트하는 메서드입니다.
    // 사용자 객체의 ID를 기준으로 users 테이블에서 해당 사용자의 정보를 갱신합니다.
	@Override
	public void update(User user) {
		this.jdbcTemplate.update(
				"update users set "
				+ "name = ?, "
				+ "password = ?, "
				+ "email = ?, "
				+ "level = ?, "
				+ "login = ?, " 
				+ "recommend = ? "
				+ "where id = ? ", 
				user.getName(), 
				user.getPassword(), 
				user.getEmail(),
				user.getLevel().intValue(), 
				user.getLogin(), 
				user.getRecommend(),
				user.getId());
	}
}