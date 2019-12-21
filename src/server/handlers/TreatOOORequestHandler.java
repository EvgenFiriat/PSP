package server.handlers;

import database.DAO.RequestDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.SQLException;

public class TreatOOORequestHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        try {
            new RequestDAO().treatOOORequest((Long) data.get("requestID"), (Boolean) data.get("isApproved"));
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка бд");
        }
        return response.toJSONString() + "\n";
    }
}
