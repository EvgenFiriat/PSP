package server.handlers.init;

import database.DAO.ClassDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.base.IActionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitUserScheduleWindowHandler implements IActionHandler {
    @Override
    public String handle(JSONObject data) {
        JSONObject responseObj = new JSONObject();
        JSONArray classes = new JSONArray();

        try {
            ResultSet classesList = new ClassDAO().get(data);

            while (classesList.next()) {
                JSONObject serializedClass = new JSONObject();
                String timeString = classesList.getTime("start_time").toString() + " - " + classesList.getTime("end_time");
                String classNumber = "ауд. " + classesList.getInt("class_number");
                serializedClass.put("timeString", timeString);
                serializedClass.put("classNumber", classNumber);
                classes.add(serializedClass);
            }
            responseObj.put("classes", classes);
            responseObj.put("success", true);
        } catch (SQLException | ClassNotFoundException e) {
            responseObj.put("success", false);
            responseObj.put("errorMessage", "Ошибка при подключении к серверу БД");
        }
        return responseObj.toJSONString() + "\n";
    }
}
