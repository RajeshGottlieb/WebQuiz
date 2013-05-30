package com.webquiz;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class XmlToDb {

	public XmlToDb(String filename) {

		Properties prop = new Properties();

		try {
			// load a properties file
			prop.load(new FileInputStream("build.properties"));

			// get the database properties
			String sql_driver = prop.getProperty("sql.driver");
			String sql_url = prop.getProperty("sql.url")
					+ prop.getProperty("sql.database");
			String sql_user = prop.getProperty("sql.user");
			String sql_pass = prop.getProperty("sql.pass");

			// load the database driver
			Class.forName(sql_driver);
			System.out.println("JDBC driver loaded");

			// connect to the database
			Connection con = DriverManager.getConnection(sql_url, sql_user,
					sql_pass);
			System.out.println("Database connection established");
			Statement stmt = con.createStatement();

			// open and parse the xml file
			System.out.println("using xml file: " + filename);
			Document doc = (Document) DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(new File(filename));

			NodeList nl = doc.getElementsByTagName("user");
			for (int i = 0; i < nl.getLength(); i++)
				handleUser(nl.item(i), stmt);

			nl = doc.getElementsByTagName("subject");
			for (int i = 0; i < nl.getLength(); i++)
				handleSubject(nl.item(i), stmt);

			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void handleUser(Node node, Statement stmt) throws SQLException {
		String username = getAttribute(node, "username");
		String password = getAttribute(node, "password");

		int i = stmt
				.executeUpdate("INSERT INTO user (username, password) VALUES ('"
						+ username + "', '" + password + "')");
		System.out.println("Inserted: user row OK using Statement.  " + i
				+ " row(s)");
	}

	void handleSubject(Node node, Statement stmt) throws SQLException {
		String name = getAttribute(node, "name");

		int rc = stmt.executeUpdate("INSERT INTO subject (name) VALUES ('"
				+ name + "')", Statement.RETURN_GENERATED_KEYS);
		System.out.println("Inserted: subject row OK using Statement.  " + rc
				+ " row(s)");

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			int subject_id = rs.getInt(1);
			NodeList nl = node.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node child = nl.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					if (child.getNodeName() == "category") {
						handleCategory(child, subject_id, stmt);
					}
				}
			}
		}
	}

	void handleCategory(Node node, int subject_id, Statement stmt)
			throws SQLException {
		String name = getAttribute(node, "name");

		int rc = stmt.executeUpdate(
				"INSERT INTO category (subject_id, name) VALUES (" + subject_id
						+ ", '" + name + "')", Statement.RETURN_GENERATED_KEYS);
		System.out.println("Inserted: category row OK using Statement.  " + rc
				+ " row(s)");

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			int category_id = rs.getInt(1);
			NodeList nl = node.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node child = nl.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					if (child.getNodeName() == "module") {
						handleModule(child, category_id, stmt);
					}
				}
			}
		}
	}

	void handleModule(Node node, int category_id, Statement stmt)
			throws SQLException {
		String name = getAttribute(node, "name");

		int rc = stmt.executeUpdate(
				"INSERT INTO module (category_id, name) VALUES (" + category_id
						+ ", '" + name + "')", Statement.RETURN_GENERATED_KEYS);
		System.out.println("Inserted: module row OK using Statement.  " + rc
				+ " row(s)");
		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			int module_id = rs.getInt(1);
			NodeList nl = node.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node child = nl.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					if (child.getNodeName() == "question") {
						handleQuestion(child, module_id, stmt);
					}
				}
			}
		}
	}

	void handleQuestion(Node node, int module_id, Statement stmt)
			throws SQLException {
		String type = getAttribute(node, "type");

		NodeList nl = node.getChildNodes();

		String text = "";

		class Answer {
			Answer(String correct, String value) {
				this.correct = correct;
				this.value = value;
			}

			String correct, value;
		}

		ArrayList<Answer> answers = new ArrayList<Answer>();

		for (int i = 0; i < nl.getLength(); i++) {
			Node child = nl.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName() == "text") {
					text = getText(child);
				}
				if (child.getNodeName() == "answer") {
					Answer ans = new Answer(
							getAttribute(child, "correct", null),
							getText(child));
					answers.add(ans);
				}
			}
		}

		int rc = stmt.executeUpdate(
				"INSERT INTO question (module_id, type, text) VALUES ("
						+ module_id + ", '" + type + "', '" + text + "')",
				Statement.RETURN_GENERATED_KEYS);
		System.out.println("Inserted: question row OK using Statement.  " + rc
				+ " row(s)");
		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			int question_id = rs.getInt(1);

			for (Answer answer : answers) {
				if (answer.correct != null) {

					int correct = answer.correct.equals("true") ? 1 : 0;
					rc = stmt.executeUpdate(
							"INSERT INTO answer (question_id, correct, value) VALUES ("
									+ question_id + ", " + correct + ", '"
									+ answer.value + "')",
							Statement.RETURN_GENERATED_KEYS);
				} else {

					rc = stmt
							.executeUpdate("INSERT INTO answer (question_id, value) VALUES ("
									+ question_id + ", '" + answer.value + "')");
				}
				System.out.println("Inserted: answer row OK using Statement.  "
						+ rc + " row(s)");
			}
		}
	}

	String getAttribute(Node node, String name, String defAttr) {
		String attr = defAttr;
		Node attrNode = node.getAttributes().getNamedItem(name);
		if (attrNode != null)
			attr = attrNode.getNodeValue();
		if (attr != null)
			attr = unescape(attr);
		return attr;
	}

	String getAttribute(Node node, String name) {
		return this.getAttribute(node, name, "");
	}

	String getText(Node node) {
		return unescape(node.getTextContent());
	}

	/**
	 * unescape XML special chars
	 */
	String unescape(String str) {
		str = str.replace("&quot;", "\"");
		str = str.replace("&apos;", "'");
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&amp;", "&");
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1)
			System.err.println("usage: XmlToDb <xml-file>");
		else {
			new XmlToDb(args[0]);
		}
	}
}
