package com.jinsu.springframe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MConnectionMaker implements ConnectionMaker {
	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/sbdt_db?characterEncoding=UTF-8", 
				"root",
				"wlstnek123");
		return c;
	}
	}
