package com.project.backend.model;

import com.project.backend.model.DatabaseConnection;
import java.sql.Connection;
import java.util.*;
import java.sql.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Admin {

    private String adminId;
    private String name;
    private String password;
    public Connection connection;
    public Statement statement;
    public ResultSet resultSet;

    public Admin() {
        DatabaseConnection dbc = DatabaseConnection.getConnection();
        connection = dbc.connection;
        // System.out.println(connection);
    }

    public Admin(String adminId, String name, String password) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        DatabaseConnection dbc = DatabaseConnection.getConnection();
        connection = dbc.connection;
        // System.out.println(connection);
        // System.out.println("Nicee");
    }

    public String login(String adminId) {
        String password = "";
        try {
            String query = String.format("select password from admin where ID='%s';", adminId);

            System.out.println(connection);
            System.out.println(query);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                password = resultSet.getString("password");
                System.out.println("Password: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public ArrayList<String[]> getPendingTestData() {

        ArrayList<String[]> testData = new ArrayList<String[]>();
        String query = "select * from Test where accepted = false";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData(); // metadata of resultset
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            testData.add(columnNames);

            // Print rows and store them
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                testData.add(row);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return testData;
    }

    public ArrayList<String[]> getAcceptedTestData() {

        ArrayList<String[]> testData = new ArrayList<String[]>();
        String query = "select * from Test where accepted = true";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData(); // metadata of resultset
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            testData.add(columnNames);

            // Print rows and store them
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                testData.add(row);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return testData;
    }

    public String updateTest(String testId, String newDifficulty, String startDateTime, String endDateTime) {
        try {
            // Convert startDateTime and endDateTime strings to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateTime, formatter);

            Timestamp sqlStartDateTime = Timestamp.valueOf(startDate);
            Timestamp sqlEndDateTime = Timestamp.valueOf(endDate);

            System.out.println("SQL Start Date: " + sqlStartDateTime);
            System.out.println("SQL End Date: " + sqlEndDateTime);

            String updateQuery = "UPDATE test SET Difficulty = ?, StartDateTime = ?, EndDateTime = ? WHERE TestID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, newDifficulty);
            preparedStatement.setTimestamp(2, sqlStartDateTime);
            preparedStatement.setTimestamp(3, sqlEndDateTime);
            preparedStatement.setString(4, testId);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Works";
    }

    public String getAdminId() {
        return this.adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}