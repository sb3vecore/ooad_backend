package com.project.backend.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StudentDatabaseModel {

    DatabaseConnection database = null;

    public StudentDatabaseModel() {
        this.database = DatabaseConnection.getConnection();
    }

    public void storeStudentResponses(Result result) {
        try {
            
            Statement statement = this.database.connection.createStatement();

            for(Map.Entry<String, String> entry : result.getMarkedOptions().entrySet()) {
                statement.executeUpdate(String.format("INSERT INTO Student_Answers VALUES (\"%s\", \"%s\", \"%s\", \"%s\");", 
                    result.getSRN(),
                    result.getTestId(),
                    entry.getKey(),
                    entry.getValue()
                ));
            }

        } catch(Exception exception) {
            System.out.println(exception);
        }
    }

    public int calculateStudentTotal(Result result) {
        try {
            Statement statement = this.database.connection.createStatement();
            ResultSet resultSet = null;
            Question question;
            int totalScore = 0;
            
            for(Map.Entry<String, String> entry : result.getMarkedOptions().entrySet()) {
                resultSet = statement.executeQuery(String.format("SELECT * FROM Questions WHERE questionId = \"%s\"", entry.getKey()));
                while(resultSet.next()) {
                    question = new Question(
                        resultSet.getString("questionId"),
                        resultSet.getString("question"),
                        resultSet.getString("correct_option"),
                        resultSet.getInt("marks"),
                        new ArrayList<String>(Arrays.asList(
                            resultSet.getString("option1"),
                            resultSet.getString("option2"),
                            resultSet.getString("option3"),
                            resultSet.getString("option4")
                        ))
                    );
                    totalScore += question.evaluateQuestion(entry.getValue());
                }
            }

            result.setTotalScore(totalScore);
            resultSet.close();
            statement.close();

            return totalScore;

        } catch(Exception exception) {
            System.out.println(exception);
            return -1;
        }
    }

    public void storeTestResult(Result result) {
        try {

            Statement statement = this.database.connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO Student_Test VALUES (\"%s\", \"%s\", %d)", 
                result.SRN,
                result.testId,
                result.totalScore
            ));

        } catch(Exception exception) {
            System.out.println(exception);
        }
    }

}
