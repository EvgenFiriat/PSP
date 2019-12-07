package server.base;

import org.json.simple.JSONObject;

import java.sql.SQLException;

public interface IActionHandler {
    String handle(JSONObject data);
}
