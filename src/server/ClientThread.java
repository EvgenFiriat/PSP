package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.base.IActionHandler;
import server.handlers.AddUserHandler;
import server.handlers.LoginHandler;
import server.handlers.init.InitAddUserModalHandler;
import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
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
                JSONObject request = (JSONObject) new JSONParser().parse(JSONDataString);
                String JSONResult = handlersMap.get((String)request.get("action")).handle((JSONObject)request.get("data"));
                out.write(JSONResult);
                out.flush();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedReader getReader() {
        return this.in;
    }

    private void initHandlersMap() {
        this.handlersMap = new HashMap<>();
        this.handlersMap.put(Constants.ACTION_LOGIN, new LoginHandler());
        this.handlersMap.put(Constants.ACTION_CREATE_USER, new AddUserHandler());
        this.handlersMap.put(Constants.ACTION_INIT_ADD_USER_MODAL, new InitAddUserModalHandler());
    }

    public BufferedWriter getWriter() {
        return this.out;
    }
}
