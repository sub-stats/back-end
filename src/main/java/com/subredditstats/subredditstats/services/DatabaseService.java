package com.subredditstats.subredditstats.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseService {
    private final String url = "dummy";
    private final String user = "dummy";
    private final String password = "dummy";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Failure creating the connection" + e.getMessage());
        }

        return conn;
    }
}
