package server.handlers.init;

import database.DAO.UserLevelDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitAddUserModalHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        JSONArray menuChoices = new JSONArray();
        try {
            UserLevelDAO dao = new UserLevelDAO();
            ResultSet choices = dao.list();
            while (choices.next()) {
                menuChoices.add(choices.getString("level_name"));
            }
            response.put("success", true);
            response.put("menuChoices", menuChoices);
        } catch (SQLException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка подключения к базе данных");
        }
        return response.toJSONString() + "\n";
    }
}
