package server.handlers.init;

import database.DAO.UserDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitPieChartHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();

        try {
            ResultSet resultSet = new UserDAO().getStats();

            while (resultSet.next()) {
                response.put(resultSet.getString("user_level.level_name"), resultSet.getInt("count(user.id)"));
            }
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка инициализации");
        }

        return response.toJSONString() + "\n";
    }
}
