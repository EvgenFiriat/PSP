package server.handlers.init;

import database.DAO.UserRoleDAO;
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
            UserRoleDAO dao = new UserRoleDAO();
            ResultSet choices = dao.list();
            while (choices.next()) {
                menuChoices.add(choices.getString("role_name"));
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
