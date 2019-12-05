package database;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBQueryHandler {
    ResultSet list(JSONObject data) throws SQLException;

    ResultSet get(JSONObject data) throws SQLException;

    void add(JSONObject data) throws SQLException;

    void update(JSONObject data) throws SQLException;

    void delete(JSONObject data) throws SQLException;
}
