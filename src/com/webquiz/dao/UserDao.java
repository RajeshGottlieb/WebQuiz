package com.webquiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.webquiz.model.User;

public class UserDao {

    /**
     * Returns matching User object from the database
     * 
     * @param username
     * @param password
     * @return User or null if not found
     */
    public static User getUser(String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id FROM user WHERE username = ? AND password = ?";
        try {
            connection = JdbcManager.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(username, password, rs.getInt("id"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcManager.close(rs);
            JdbcManager.close(ps);
            JdbcManager.close(connection);
        }
        return null;
    }

    public static boolean addUser(String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id FROM user WHERE username = ?";
        try {
            connection = JdbcManager.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                String insert = "INSERT INTO user (username, password) VALUES ('" + username + "', '" + password + "')";
                java.sql.Statement stmt = connection.createStatement();
                stmt.executeUpdate(insert);
                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcManager.close(rs);
            JdbcManager.close(ps);
            JdbcManager.close(connection);
        }
        return false;
    }
}
