package com.yootk.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.25.134:3306/yootk";
    private static final String DB_USER = "yootk";
    private static final String DB_PASS = "Yootk@123";
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    private DatabaseConnect() {}

    private static Connection rebuildConnection() {
        Connection conn = null ;

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getConnection() {
        Connection conn = THREAD_LOCAL.get();
        if(conn == null) {
            conn = rebuildConnection();
            THREAD_LOCAL.set(conn);
        }
        return conn;
    }

    public static void close() {
        Connection conn = THREAD_LOCAL.get();
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            THREAD_LOCAL.remove();
        }
    }

    public static void main(String[] args) {
        System.out.println("==conn==" + DatabaseConnect.getConnection());
        DatabaseConnect.close();
        System.out.println("==conn==close==");
    }
}
