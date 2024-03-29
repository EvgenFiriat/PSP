package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.base.IActionHandler;
import server.handlers.*;
import server.handlers.init.*;
import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientThread extends Thread {
    private BufferedWriter out;
    private BufferedReader in;
    private Map<String, IActionHandler> handlersMap;

    ClientThread(Socket connectedClient) {
        this.initHandlersMap();
        try {
            in = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(connectedClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                String JSONDataString = in.readLine();
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
        this.handlersMap.put(Constants.ACTION_INIT_USER_WINDOW, new InitUserWindowHandler());
        this.handlersMap.put(Constants.ACTION_INIT_VIEW_USERS_WINDOW, new InitViewUsersWindowHandler());
        this.handlersMap.put(Constants.ACTION_BLOCK_USER, new BlockUserHandler());
        this.handlersMap.put(Constants.ACTION_INIT_OOO_REQUEST_WINDOW, new InitOOORequestWindowHandler());
        this.handlersMap.put(Constants.ACTION_CREATE_OOO_REQUEST, new OOORequestHandler());
        this.handlersMap.put(Constants.ACTION_INIT_ADMIN_NOTIFICATIONS_WINDOW, new InitAdminNotificationsWindowHandler());
        this.handlersMap.put(Constants.ACTION_TREAT_OOO_REQUEST, new TreatOOORequestHandler());
        this.handlersMap.put(Constants.ACTION_INIT_USER_NOTIFICATIONS_WINDOW, new InitUserNotificationsWindowHandler());
        this.handlersMap.put(Constants.ACTION_INIT_EDIT_USER_WINDOW, new InitEditUserModalHandler());
        this.handlersMap.put(Constants.ACTION_EDIT_USER, new EditAccountHandler());
        this.handlersMap.put(Constants.ACTION_INIT_PIE_CHART, new InitPieChartHandler());
        this.handlersMap.put(Constants.ACTION_INIT_SCHEDULE_WINDOW, new InitUserScheduleWindowHandler());
    }

    public BufferedWriter getWriter() {
        return this.out;
    }
}
