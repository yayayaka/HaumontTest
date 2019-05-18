package com.haulmont.testtask;

import java.io.IOException;
import java.sql.*;

public class TestDB {
    Connection conn = null;

    public TestDB() throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/hospitalDB", "SA", "");
        System.out.println("Closed = " + conn.isClosed());
        Statement statement = conn.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Patient");

    }
}
