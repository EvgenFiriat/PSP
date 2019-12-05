package server;

import database.MySqlConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private boolean working = true;

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(1488);
            System.out.println("Server started on port 1488");
            while (this.working) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.working = false;
        }
    }
}
