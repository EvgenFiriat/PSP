package server.handlers;

import database.DAO.RequestDAO;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.SQLException;

public class OOORequestHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        RequestDAO requestDAO = new RequestDAO();
        try {
            requestDAO.createOOORequest(data);
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errorMessage", "Ошибка подключения к бд");
        }
        return response.toJSONString() + "\n";
    }
}
