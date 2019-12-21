package database.DAO;

import database.DBQueryHandler;
import database.MySqlConnection;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassDAO implements DBQueryHandler {


    @Override
    public ResultSet list() {
        return null;
    }

    @Override
    public ResultSet get(JSONObject data) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM class WHERE teacher_id = ? AND DAYOFWEEK(NOW()) = weekday ORDER BY start_time";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, (Long) data.get("id"));
        return query.executeQuery();
    }

    public String getScheduledClass(Long userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM class WHERE teacher_id = ? AND TIME(NOW()) BETWEEN start_time AND end_time AND DAYOFWEEK(NOW()) = weekday";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, userId);
        ResultSet resultSet = query.executeQuery();
        if (resultSet.next()) {
            return "Пара в " + resultSet.getInt("class_number");
        } else {
            return "Пар нет";
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
