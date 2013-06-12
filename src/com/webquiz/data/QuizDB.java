package com.webquiz.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.webquiz.business.Answer;
import com.webquiz.business.Question;
import com.webquiz.business.Quiz;

public class QuizDB {

    /**
     * Populates a QuizSelection object from the database
     * 
     * @param user
     * @return
     */
    public static Quiz generate(int[] modules, int maxQuestionCount) {
        Quiz quiz = new Quiz();
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            quiz.setQuestions(getQuestions(connection, modules, maxQuestionCount));

            for (Question question : quiz.getQuestions())
                question.setAnswers(getAnswers(connection, question));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(connection);
        }
        return quiz;
    }

    static String qmarks(int count) {
        StringBuilder str = new StringBuilder();
        String separator = "";

        for (int i = 0; i < count; i++) {
            str.append(separator);
            str.append("?");
            separator = ", ";
        }
        return str.toString();
    }

    private static Question.Type questionType(String type) {
        if (type.equals(Question.Type.MULTIPLE_CHOICE.name()))
            return Question.Type.MULTIPLE_CHOICE;
        else if (type.equals(Question.Type.TRUE_FALSE.name()))
            return Question.Type.TRUE_FALSE;
        else if (type.equals(Question.Type.FILL_IN_THE_BLANK.name()))
            return Question.Type.FILL_IN_THE_BLANK;
        else
            return Question.Type.UNKNOWN;
    }

    static ArrayList<Question> getQuestions(Connection connection, int[] modules, int maxQuestionCount)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Question> questions = new ArrayList<Question>();

        String query = "SELECT id, text, type FROM question WHERE module_id IN (" + qmarks(modules.length)
                + ") ORDER BY RAND() LIMIT " + maxQuestionCount;

        try {
            ps = connection.prepareStatement(query);

            for (int i = 0; i < modules.length; i++)
                ps.setInt(i + 1, modules[i]);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Question.Type type = questionType(rs.getString("type"));
                String text = rs.getString("text");

                Question question = new Question(id, type, text);
                System.out.println("question=" + question.getText() + " type=" + type);
                questions.add(question);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
        }
        return questions;
    }

    private static ArrayList<Answer> getAnswers(Connection connection, Question question) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Answer> answers = new ArrayList<Answer>();

        String query = "SELECT id, correct, value FROM answer WHERE question_id = ? ORDER BY RAND()";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, question.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                boolean correct = rs.getInt("correct") == 1;
                String value = rs.getString("value");

                Answer answer = new Answer(id, correct, value);
                System.out.println("answer=" + answer.getValue());
                answers.add(answer);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
        }
        return answers;
    }
}
