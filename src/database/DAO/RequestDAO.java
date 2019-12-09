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

    public Long getRequestTypeIdByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id from request_type WHERE name = ? LIMIT 1";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setString(1, name);
        ResultSet result = query.executeQuery();
        result.next();
        return result.getLong("id");
    }

    public void createOOORequest(JSONObject data) throws SQLException, ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        RequestDAO requestDAO = new RequestDAO();
        Long approverID = userDAO.getUserIdByName((String) data.get("approver"));
        Long requestorID = (Long) data.get("requestorID");
        Long requestTypeID = requestDAO.getRequestTypeIdByName((String) data.get("requestType"));
        String startDate = (String) data.get("startDate");
        String endDate = (String) data.get("endDate");
        String comment = (String) data.get("comment");
        String sql = "INSERT INTO ooo_request(user_id, approver_id, request_type_id, comment, start_date, end_date) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, requestorID);
        query.setLong(2, approverID);
        query.setLong(3, requestTypeID);
        query.setString(4, comment);
        query.setString(5, startDate);
        query.setString(6, endDate);
        query.executeUpdate();
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
