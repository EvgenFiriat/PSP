package database;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBQueryHandler {
    ResultSet list(JSONObject data);

    ResultSet get(JSONObject data);

    void add(JSONObject data);

    void update(JSONObject data);

    void delete(JSONObject data);
}
