package com.jinsu.springframe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jinsu.springframe.domain.User;


public class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();

		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}


	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	// 리팩토링이 추가됨 ...중복된 Drive 클래스 로드 코드와 중복된 데이터베이스 연결코드를 하나의 메서드 통
	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.h2.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:h2://localhost/sbdt_db?characterEncoding=UTF-8",
				"jinsukim",
				"wlstnek123");
		return c;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		UserDao dao = new UserDao();
		Connection c = dao.getConnection();
		
		System.out.println("Hello");
		
	
		
		
		//		UserDao dao = new UserDao();
//
//		User user = new User();
//		user.setId("jinsu");
//		user.setName("진수");
//		user.setPassword("married");
//
//		dao.add(user);
//			
//		System.out.println(user.getId() + " 등록 성공");
//		
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());
//			
//		System.out.println(user2.getId() + " 조회 성공");
//	}
//
//}
	}
}

