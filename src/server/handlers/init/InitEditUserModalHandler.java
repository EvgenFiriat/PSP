package server.handlers.init;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitEditUserModalHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        try {
            ResultSet result = new UserDAO().get(data);
            result.next();
            response.put("name", result.getString("name"));
            response.put("surname", result.getString("surname"));
            response.put("password", result.getString("password"));
            response.put("phone", result.getString("phone_number"));
            response.put("skype", result.getString("skype"));
            response.put("email", result.getString("email"));
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            response.put("success", false);
            response.put("errorMessage", "Ошибка БД");
        }

        return response.toJSONString() + "\n";
    }
}
