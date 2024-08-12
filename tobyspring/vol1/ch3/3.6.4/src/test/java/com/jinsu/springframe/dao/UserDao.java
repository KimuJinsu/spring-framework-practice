package com.jinsu.springframe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jinsu.springframe.domain.User;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }

    public void add(final User user) {
        this.jdbcTemplate.update(
            "INSERT INTO users(id, name, password) VALUES(?,?,?)",
            user.getId(), user.getName(), user.getPassword()
        );
    }

    public Optional<User> get(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = this.jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                userRowMapper()
            );
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM users",
            Integer.class
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
            "SELECT * FROM users ORDER BY id",
            userRowMapper()
        );
    }
}