package server.handlers;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.SQLException;

public class BlockUserHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        try {
            new UserDAO().blockUser(data);
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            response.put("success", false);
            response.put("errorMessage", "Не удалось заблокировать пользователя");
        }
        return response.toJSONString() + "\n";
    }
}
