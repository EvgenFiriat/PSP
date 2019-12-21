package server.handlers.init;

import database.DAO.RequestDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitAdminNotificationsWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        JSONArray requests = new JSONArray();
        try {

            ResultSet result = new RequestDAO().getRequestsByApprover((Long) data.get("id"));
            JSONObject obj = new JSONObject();
            while (result.next()) {
                obj.put("id", result.getLong("id"));
                obj.put("name", result.getString("user.name"));
                obj.put("surname", result.getString("user.surname"));
                obj.put("comment", result.getString("comment"));
                obj.put("requestType", result.getString("request_type.name"));
                obj.put("date", result.getDate("start_date").toString() + " - " + result.getDate("end_date"));
                requests.add(obj);
            }
            response.put("notifications", requests);
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка базы данных");
        }
        return response.toJSONString() + "\n";
    }
}
