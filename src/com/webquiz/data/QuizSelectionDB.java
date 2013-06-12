package com.webquiz.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.webquiz.model.QuizSelection;
import com.webquiz.model.Subject;
import com.webquiz.model.Category;
import com.webquiz.model.Module;

public class QuizSelectionDB {

    /**
     * Populates a QuizSelection object from the database
     * 
     * @param user
     * @return
     */
    public static QuizSelection populate() {
        QuizSelection selection = new QuizSelection();
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            selection.setSubjects(getSubjects(connection));

            for (Subject subject : selection.getSubjects()) {
                subject.setCategories(getCategories(connection, subject));
                
                for (Category category : subject.getCategories()) {
                    category.setModules(getModules(connection, category));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(connection);
        }
        return selection;
    }

    static ArrayList<Subject> getSubjects(Connection connection) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        ArrayList<Subject> subjects = new ArrayList<Subject>();

        try {
            stmt = connection.createStatement();

            rs = stmt.executeQuery("SELECT name, id FROM subject");
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");

                Subject subject = new Subject(name, id);
                System.out.println("subject=" + subject.getName());
                subjects.add(subject);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return subjects;
    }

    static ArrayList<Category> getCategories(Connection connection, Subject subject) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Category> categories = new ArrayList<Category>();

        String query = "SELECT name, id FROM category WHERE subject_id = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, subject.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                Category category = new Category(name, id);
                System.out.println("category=" + category.getName());
                categories.add(category);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
        }
        return categories;
    }
    
    static ArrayList<Module> getModules(Connection connection, Category category) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Module> modules = new ArrayList<Module>();

        String query = "SELECT name, id FROM module WHERE category_id = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, category.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                Module module = new Module(name, id);
                System.out.println("module=" + module.getName());
                modules.add(module);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
        }
        return modules;
    }
}
