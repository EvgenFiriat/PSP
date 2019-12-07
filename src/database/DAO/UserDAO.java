package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO implements DBQueryHandler {

    @Override
    public ResultSet list() {
        String sql = "SELECT * FROM user";
        try {
            PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
            return query.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUserUnique(JSONObject data) {
        String sql = "SELECT * FROM user WHERE (name = ? AND surname = ?) OR email = ?";
        try {
            PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
            query.setString(1, (String) data.get("name"));
            query.setString(2, (String) data.get("surname"));
            query.setString(3, (String) data.get("email"));
            boolean hasEntries = query.executeQuery().next();
            return !hasEntries;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet authenticate(JSONObject data) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
            query.setString(1, (String) data.get("email"));
            query.setString(2, (String) data.get("password"));
            return query.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getUserWindowContext(Long id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT user.name as name, surname, email, skype, role_name as position, phone_number as phone, project.name as project FROM user " +
                "JOIN user_position on user.position_id = user_position.id " +
                "JOIN project on user.project_id = project.id " +
                "WHERE user.id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, id);
        return query.executeQuery();
    }

    @Override
    public ResultSet get(JSONObject data) {
        return null;
    }

    @Override
    public void add(JSONObject data) throws SQLException, ClassNotFoundException {
        ResultSet project = new ProjectDAO().get(data);
        String sql = "INSERT user(" +
                "name, " +
                "surname, " +
                "email, " +
                "password, " +
                "skype, " +
                "phone_number, " +
                "salary, " +
                "is_admin, " +
                "position_id, " +
                "project_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        project.next();
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, (String) data.get("name"));
        query.setString(2, (String) data.get("surname"));
        query.setString(3, (String) data.get("email"));
        query.setString(4, (String) data.get("password"));
        query.setString(5, (String) data.get("skype"));
        query.setString(6, (String) data.get("phone"));
        query.setDouble(7, (Double) data.get("salary"));
        query.setBoolean(8, (Boolean) data.get("isAdmin"));
        query.setInt(9, 1);
        query.setInt(10, project.getInt("id"));
        query.executeUpdate();
    }

    @Override
    public void update(JSONObject data) {

    }

    @Override
    public void delete(JSONObject data) {

    }
}
