package server.handlers.init;

import database.DAO.UserDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitViewUsersWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject responseObj = new JSONObject();
        JSONArray users = new JSONArray();

        try {
            ResultSet result = new UserDAO().getUsers();
            while (result.next()) {
                JSONObject serializedUser = new JSONObject();
                serializedUser.put("id", result.getLong("user.id"));
                serializedUser.put("name", result.getString("name"));
                serializedUser.put("surname", result.getString("surname"));
                serializedUser.put("email", result.getString("email"));
                serializedUser.put("projectName", result.getString("project"));
                serializedUser.put("position", result.getString("position"));
                users.add(serializedUser);
            }
            responseObj.put("users", users);
            responseObj.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            responseObj.put("success", false);
            responseObj.put("errorMessage", "Ошибка при подключении к серверу БД");
        }
        return responseObj.toJSONString() + "\n";
    }
}
