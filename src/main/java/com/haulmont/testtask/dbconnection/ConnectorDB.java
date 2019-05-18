package com.haulmont.testtask.dbconnection;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB implements Closeable {
    private Connection conn;

    public ConnectorDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/hospitalDB", "SA", "");
        } catch (ClassNotFoundException | SQLException e) {
            conn = null;
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    @Override
    public void close() {
        try {
            if (!conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            conn = null;
        }
    }
}
