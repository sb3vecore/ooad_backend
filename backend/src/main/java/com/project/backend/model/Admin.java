package com.project.backend.model;
import com.project.backend.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    private int adminId;
    private String name;

    public Admin (int adminId, String name) {
        this.adminId = adminId;
        this.name = name;
        DatabaseConnection dbc = DatabaseConnection.getConnection();
        Connection connection = dbc.connection;        
    }

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
