package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO implements DBQueryHandler {

    public ResultSet listAdmins() throws SQLException, ClassNotFoundException {
        String sql = "SELECT name, surname FROM user WHERE is_admin = 1";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        return query.executeQuery();

    }

    public ResultSet getStats() throws SQLException, ClassNotFoundException {
        String sql = "SELECT user_level.level_name, count(user.id) from user " +
                "JOIN user_level ON user_level.id = user.level_id group by user_level.level_name";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        return query.executeQuery();
    }

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

    public Long getUserIdByName(String name) throws SQLException, ClassNotFoundException {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        String sql = "SELECT id FROM user WHERE name = ? AND surname = ? AND is_admin = 1 LIMIT 1";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, firstName);
        query.setString(2, lastName);
        ResultSet result = query.executeQuery();
        result.next();
        return result.getLong("id");
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
        String sql = "SELECT user.name as name, surname, email, skype, level_name as level, phone_number as phone FROM user " +
                "JOIN user_level on user.level_id = user_level.id " +
                "WHERE user.id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, id);
        return query.executeQuery();
    }

    public void updateUser(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET name = ?, surname = ?, email = ?, phone_number = ?, password = ?, skype = ? WHERE id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, (String) data.get("name"));
        query.setString(2, (String) data.get("surname"));
        query.setString(3, (String) data.get("email"));
        query.setString(4, (String) data.get("phone"));
        query.setString(5, (String) data.get("password"));
        query.setString(6, (String) data.get("skype"));
        query.setLong(7, (Long) data.get("userID"));
        query.executeUpdate();
    }

    public void blockUser(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET is_banned=TRUE WHERE user.id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, (Long) data.get("userID"));
        query.executeUpdate();
    }

    public ResultSet getUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT user.id, user.name as name, level_name as level, surname, email, is_banned FROM user " +
                "JOIN user_level on user.level_id = user_level.id";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        return query.executeQuery();
    }

    @Override
    public ResultSet get(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * from user WHERE id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, (Long) data.get("userID"));
        return query.executeQuery();
    }

    @Override
    public void add(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "INSERT user(" +
                "name, " +
                "surname, " +
                "email, " +
                "password, " +
                "skype, " +
                "phone_number, " +
                "is_admin, " +
                "level_id ) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, (String) data.get("name"));
        query.setString(2, (String) data.get("surname"));
        query.setString(3, (String) data.get("email"));
        query.setString(4, (String) data.get("password"));
        query.setString(5, (String) data.get("skype"));
        query.setString(6, (String) data.get("phone"));
        query.setBoolean(7, (Boolean) data.get("isAdmin"));
        query.setLong(8, new UserLevelDAO().getLevelId((String) data.get("position")));
        query.executeUpdate();
    }

    @Override
    public void update(JSONObject data) {

    }

    @Override
    public void delete(JSONObject data) {

    }
}
