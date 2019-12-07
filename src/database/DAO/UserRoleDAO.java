package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDAO implements DBQueryHandler {

    @Override
    public ResultSet list() {
        String sql = "SELECT * FROM user_position";
        try {
            PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
            return query.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
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
