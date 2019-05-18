package com.haulmont.testtask;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            TestDB db = new TestDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
