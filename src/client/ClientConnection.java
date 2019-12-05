package client;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private static BufferedReader in;
    private static BufferedWriter out;
    private static ClientConnection connection;

    public static ClientConnection getConnection() throws IOException {
        if (connection == null)
            connection = new ClientConnection();
        return connection;
    }

    private ClientConnection() throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 1488);
        System.out.println("Connection accepted " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static BufferedWriter getOut() {
        return out;
    }

    public static void disconnect() {

    }
}
