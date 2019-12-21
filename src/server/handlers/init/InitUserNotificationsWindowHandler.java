package server.handlers.init;

import database.DAO.RequestDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitUserNotificationsWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        JSONArray requests = new JSONArray();
        try {

            ResultSet result = new RequestDAO().getSentRequests((Long) data.get("id"));

            while (result.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", result.getLong("id"));
                obj.put("approverName", result.getString("user.name") + " " + result.getString("user.surname"));
                obj.put("requestType", result.getString("request_type.name"));
                obj.put("date", result.getDate("start_date").toString() + " - " + result.getDate("end_date"));
                obj.put("isApproved", result.getBoolean("ooo_request.is_approved") ? "Подтвержден" : "Не подтвержден");
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
