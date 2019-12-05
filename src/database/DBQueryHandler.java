package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBQueryHandler {
    ResultSet list(String data) throws SQLException;

    ResultSet get(String data) throws SQLException;

    void add(String data) throws SQLException;

    void update(String data) throws SQLException;

    void delete(String data) throws SQLException;
}
