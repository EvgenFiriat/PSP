package server.handlers.init;

import database.DAO.ClassDAO;
import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitUserWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        JSONObject responseData = new JSONObject();
        try {
            ResultSet result = new UserDAO().getUserWindowContext((Long) data.get("userId"));
            if (result.next()) {
                responseData.put("email", result.getString("email"));
                responseData.put("skype", result.getString("skype"));
                responseData.put("fullName", result.getString("name") + " " + result.getString("surname"));
                responseData.put("level", "Учитель " + result.getString("level"));
                responseData.put("phone", result.getString("phone"));
                responseData.put("classData", new ClassDAO().getScheduledClass((Long) data.get("userId")));
                response.put("data", responseData);
                response.put("success", true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка инициализации");
        }
        return response.toJSONString() + "\n";
    }
}
