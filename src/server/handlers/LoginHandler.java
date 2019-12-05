package server.handlers;

import server.base.IActionHandler;

public class LoginHandler implements IActionHandler {
    @Override
    public String handle() {
        System.out.println("Login attempt...");
        return "success\n";
    }
}
