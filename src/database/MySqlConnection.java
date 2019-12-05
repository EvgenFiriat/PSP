package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySqlConnection {
    private Connection connection;
    private static MySqlConnection instance;

    public MySqlConnection() throws ClassNotFoundException, SQLException {

        // TODO: move to the DB config
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/sms" + "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static MySqlConnection getConnection() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new MySqlConnection();
        }
        return instance;
    }
}
