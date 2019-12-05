package server.base;

import org.json.simple.JSONObject;

public interface IActionHandler {
    String handle(JSONObject data);
}
