package com.jinsu.springframe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// MySql 서버용...
public class MUserDao extends UserDao {	

	protected Connection getConnection() throws ClassNotFoundException,
		SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/sbdt_db?characterEncoding=UTF-8", 
				"root",
				"wlstnek123");
		return c;
	}
}