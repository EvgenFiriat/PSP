package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO implements DBQueryHandler {


    @Override
    public ResultSet list() {
        return null;
    }

    @Override
    public ResultSet get(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM project WHERE name = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, "Smile Direct Club");
        return query.executeQuery();
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
