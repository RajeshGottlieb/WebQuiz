package com.webquiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.webquiz.model.QuizSelection;
import com.webquiz.model.Subject;
import com.webquiz.model.Category;
import com.webquiz.model.Module;

public class QuizSelectionDao {

	/**
	 * Populates a QuizSelection object from the database
	 * 
	 * @param user
	 * @return
	 */
	public QuizSelection populate() {
		QuizSelection selection = new QuizSelection();

		selection.setSubjects(getSubjects());

		for (Subject subject : selection.getSubjects()) {
			subject.setCategories(getCategories(subject));

			for (Category category : subject.getCategories()) {
				category.setModules(getModules(category));
			}
		}

		return selection;
	}

	ArrayList<Subject> getSubjects() {
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		ArrayList<Subject> subjects = new ArrayList<Subject>();

		try {
			connection = JdbcManager.getConnection();
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT name, id FROM subject");
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");

				Subject subject = new Subject(name, id);
				System.out.println("subject=" + subject.getName());
				subjects.add(subject);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();

		}
		finally {

			JdbcManager.close(rs);
			JdbcManager.close(stmt);
			JdbcManager.close(connection);

		}
		return subjects;
	}

	ArrayList<Category> getCategories(Subject subject) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		Connection connection = null;

		try {        
			connection = JdbcManager.getConnection();

			String query = "SELECT name, id FROM category WHERE subject_id = ?";

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

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JdbcManager.close(rs);
			JdbcManager.close(ps);
			JdbcManager.close(connection);

		}
		return categories;
	}

	ArrayList<Module> getModules(Category category) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;

		ArrayList<Module> modules = new ArrayList<Module>();

		String query = "SELECT name, id FROM module WHERE category_id = ?";

		try {
			connection = JdbcManager.getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(rs);
			JdbcManager.close(ps);
			JdbcManager.close(connection);
		}
		return modules;
	}
}
