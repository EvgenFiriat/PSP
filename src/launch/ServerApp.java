package launch;

import database.MySqlConnection;
import server.Server;


public class ServerApp {
    private static MySqlConnection connection;
    public static void main(String[] args) {
        new Server().start();
    }
}
