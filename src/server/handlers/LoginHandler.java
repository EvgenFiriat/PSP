package server.handlers;

import server.base.IActionHandler;

public class LoginHandler implements IActionHandler {
    @Override
    public void handle() {
        System.out.println("Login...");
    }
}
