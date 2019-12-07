package database;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBQueryHandler {
    ResultSet list();

    ResultSet get(JSONObject data) throws SQLException, ClassNotFoundException;

    void add(JSONObject data) throws SQLException, ClassNotFoundException;

    void update(JSONObject data);

    void delete(JSONObject data);
}
