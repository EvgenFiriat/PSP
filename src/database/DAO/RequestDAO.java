package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestDAO implements DBQueryHandler {

    public ResultSet getChoices() throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM request_type";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        return query.executeQuery();
    }

    @Override
    public ResultSet list() {
        return null;
    }

    @Override
    public ResultSet get(JSONObject data) {
        return null;
    }

    @Override
    public void add(JSONObject data) {

    }

    @Override
    public void update(JSONObject data) {

    }

    @Override
    public void delete(JSONObject data) {

    }
}
