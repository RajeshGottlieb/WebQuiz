package com.webquiz.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

	private static ConnectionPool pool = null;
	private static DataSource dataSource = null;

	private ConnectionPool() {

		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/web_quiz");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		if (pool == null)
			pool = new ConnectionPool();
		return pool;
	}

	public Connection getConnection() {
		Connection c = null;
		try {
			c = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public void freeConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
