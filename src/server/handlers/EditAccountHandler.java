package server.handlers;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.SQLException;

public class EditAccountHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        try {
            new UserDAO().updateUser(data);
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка бд");
        }
        return response.toJSONString() + "\n";
    }
}
