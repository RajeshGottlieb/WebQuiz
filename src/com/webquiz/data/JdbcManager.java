package com.webquiz.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcManager {

    private static InitialContext context = null;

    public static Connection getConnection() throws NamingException, SQLException {
        if (context == null)
            context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/web_quiz");
        return dataSource.getConnection();
    }

    public static void close(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement s) {
        try {
            if (s != null)
                s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
