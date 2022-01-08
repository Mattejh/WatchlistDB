package org.matt.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private final static String url = "jdbc:mariadb://localhost:3306/MoviesDB";
    private final static String user = "root";
    private final static String password = "root";

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        }
        catch (SQLException e) {
            System.out.println("Could not establish connection: " + e.getSQLState());
        }
        return null;
    }
}
