package server;

import server.base.IActionHandler;
import server.handlers.LoginHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientThread extends Thread {
    private BufferedWriter out;
    private BufferedReader in;
    private Socket client;
    private boolean isRunning;
    private String JSONDataString;
    private Map<String, IActionHandler> handlersMap;

    ClientThread(Socket connectedClient) {
        this.client = connectedClient;
        this.initHandlersMap();
        try {
            in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.isRunning = true;
        while (isRunning) {
            try {
                JSONDataString = in.readLine();
                String JSONResult = handlersMap.get(JSONDataString).handle();
                out.write(JSONResult);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedReader getReader() {
        return this.in;
    }

    private void initHandlersMap() {
        this.handlersMap = new HashMap<>();
        this.handlersMap.put("actionLogin", new LoginHandler());
    }

    public BufferedWriter getWriter() {
        return this.out;
    }
}
