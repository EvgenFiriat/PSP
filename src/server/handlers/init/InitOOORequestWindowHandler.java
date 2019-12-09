package server.handlers.init;

import database.DAO.RequestDAO;
import database.DAO.UserDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitOOORequestWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject response = new JSONObject();
        JSONArray approversList = new JSONArray();
        JSONArray requestChoicesList = new JSONArray();
        try {
            ResultSet approvers = new UserDAO().listAdmins();
            ResultSet requestTypeChoices = new RequestDAO().getChoices();

            while (approvers.next()) {
                String fullName = approvers.getString("name") + " " + approvers.getString("surname");
                approversList.add(fullName);
            }

            while (requestTypeChoices.next()) {
                requestChoicesList.add(requestTypeChoices.getString("name"));
            }

            response.put("approvers", approversList);
            response.put("choices", requestChoicesList);
            response.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            response.put("success", false);
            response.put("errorMessage", "Ошибка БД");
        }
        return response.toJSONString() + "\n";
    }
}
