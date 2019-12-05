package server.handlers;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;

public class AddUserHandler implements IActionHandler {

    @Override
    public String handle(JSONObject data) {
        UserDAO userDAO = new UserDAO();
        JSONObject response = new JSONObject();
        if (userDAO.isUserUnique(data)) {
            userDAO.add(data);
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("errorMessage", "Такой пользователь уже существует");
        }
        return response.toJSONString() + "\n";
    }
}
