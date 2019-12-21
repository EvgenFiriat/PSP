package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLevelDAO implements DBQueryHandler {

    @Override
    public ResultSet list() {
        String sql = "SELECT * FROM user_level";
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

    public Long getLevelId(String levelName) {
        String sql = "SELECT id FROM user_level WHERE level_name = ?";
        try {
            PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
            query.setString(1, levelName);
            ResultSet result = query.executeQuery();
            if (result.next()) {
                return result.getLong("id");
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
