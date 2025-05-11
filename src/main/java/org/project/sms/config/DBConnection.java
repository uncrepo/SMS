package org.project.sms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sms_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    public static Connection connection;

    public DBConnection() {
        try {
            this.connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }

}
