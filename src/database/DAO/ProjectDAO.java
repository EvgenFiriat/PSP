package database.DAO;

import database.DBQueryHandler;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO implements DBQueryHandler {
    @Override
    public ResultSet list(JSONObject data) throws SQLException {
        return null;
    }

    @Override
    public ResultSet get(JSONObject data) throws SQLException {
        return null;
    }

    @Override
    public void add(JSONObject data) throws SQLException {

    }

    @Override
    public void update(JSONObject data) throws SQLException {

    }

    @Override
    public void delete(JSONObject data) throws SQLException {

    }
}
