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

    public ResultSet getSentRequests(Long id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ooo_request.id, user.name, request_type.name, user.surname, ooo_request.is_approved, start_date, end_date FROM ooo_request " +
                "JOIN request_type on request_type_id = request_type.id " +
                "JOIN user on user.id = ooo_request.approver_id " +
                "WHERE ooo_request.user_id = ? AND is_approved IS NOT NULL";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, id);
        return query.executeQuery();
    }

    public ResultSet getRequestsByApprover(Long id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ooo_request.id, user.name, request_type.name, user.surname, ooo_request.comment, ooo_request.start_date, ooo_request.end_date FROM ooo_request " +
                "JOIN request_type on request_type_id = request_type.id " +
                "JOIN user on user.id = ooo_request.user_id " +
                "WHERE ooo_request.approver_id = ? AND is_approved IS NULL";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setLong(1, id);
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

    public void treatOOORequest(Long requestID, boolean isApproved) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE ooo_request SET is_approved = ? WHERE id = ?";
        PreparedStatement query = MySqlConnection.getConnection().prepareStatement(sql);
        query.setBoolean(1, isApproved);
        query.setLong(2, requestID);
        query.executeUpdate();
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
