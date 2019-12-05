package server.handlers;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        UserDAO userDAO = new UserDAO();
        ResultSet result = userDAO.authenticate(data);
        JSONObject response = new JSONObject();

        try {
            if (result.next()) {
                response.put("success", true);
                response.put("user_id", result.getInt("user.id"));
                String role = result.getBoolean("is_admin") ? "admin": "user";
                response.put("role", role);
            } else {
                response.put("success", false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response.toJSONString() + "\n";
    }
}
