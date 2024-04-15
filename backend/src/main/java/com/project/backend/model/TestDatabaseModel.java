package com.project.backend.model;

import java.sql.*;
import java.util.*;

public class TestDatabaseModel {

    DatabaseConnection database = null;

    public TestDatabaseModel() {
        this.database = DatabaseConnection.getConnection();
    }

    public boolean checkTeacherId(String teacherId) {
        try {
            Statement statement = this.database.connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(String.format("SELECT ID FROM Teacher WHERE ID = \"%s\";", teacherId));
            String databaseTeacherId = "";

            while (resultSet.next()) {
                databaseTeacherId = resultSet.getString("ID");
            }
            resultSet.close();
            statement.close();

            if (teacherId.equals(databaseTeacherId)) {
                return true;
            }
            return false;

        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
    }

    public int storeQuestions(ArrayList<Question> questions) {
        try {
            Statement statement = this.database.connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(String.format("SELECT COUNT(*) AS question_count FROM Questions;"));
            int oldQuestionCount = -1;
            int count;

            while (resultSet.next()) {
                oldQuestionCount = resultSet.getInt("question_count");
            }
            count = oldQuestionCount + 1;
            resultSet.close();

            for (int i = 0; i < questions.size(); i++) {
                statement.executeUpdate(String.format(
                        "INSERT INTO Questions VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %d)",
                        "Q" + count,
                        questions.get(i).question,
                        questions.get(i).options.get(0),
                        questions.get(i).options.get(1),
                        questions.get(i).options.get(2),
                        questions.get(i).options.get(3),
                        questions.get(i).answer,
                        questions.get(i).marks));
                count++;
            }

            statement.close();

            return oldQuestionCount;

        } catch (Exception exception) {
            System.out.println(exception);
            return -1;
        }
    }

    public void storeTest(int oldQuestionCount, Test test) {
        try {
            Statement statement = this.database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS test_count FROM Test;");
            int oldTestCount = -1;
            int count;

            while (resultSet.next()) {
                oldTestCount = resultSet.getInt("test_count");
            }
            resultSet.close();
            count = oldTestCount + 1;

            statement.executeUpdate(String.format(
                    "INSERT INTO Test VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %s);",
                    "TEST" + count,
                    test.getTeacherId(),
                    test.getSubject(),
                    test.getDifficulty(),
                    test.getStartDateTime(),
                    test.getEndDateTime(),
                    test.getDescription(),
                    test.getAccepted()));

            for (int i = 0; i < test.getQuestionList().size(); i++) {
                oldQuestionCount++;
                statement.executeUpdate(String.format("INSERT INTO Test_Questions VALUES (\"%s\", \"%s\");",
                        "TEST" + count,
                        "Q" + oldQuestionCount));
            }

            statement.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }

    }

    public ArrayList<Test> retrieveAllTests() {
        try {
            Statement statement = this.database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT TestID, Subject, Difficulty, StartDateTime, EndDateTime, Description from Test where Accepted = true;");
            ArrayList<Test> test = new ArrayList<Test>();
            while (resultSet.next()) {
                test.add(new Test(
                        resultSet.getString("TeacherID"),
                        resultSet.getString("TestID"),
                        resultSet.getString("Subject"),
                        resultSet.getString("Difficulty"),
                        resultSet.getString("StartDateTime"),
                        resultSet.getString("EndDateTime"),
                        resultSet.getString("Description")));
            }
            return test;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

}