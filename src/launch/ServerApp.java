package launch;

import database.MySqlConnection;

import java.sql.*;

public class ServerApp {
    private static MySqlConnection connection;
    public static void main(String[] args) {
        try {
            connection = MySqlConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
