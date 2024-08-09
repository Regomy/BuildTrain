package me.rejomy.buildtrain.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBase {
    protected abstract Connection getConnection() throws SQLException;
    protected Connection connection;
    protected Statement statement;

    public void set(String name, String islandType, long record, String args) {
        executeQuery("INSERT OR REPLACE INTO users VALUES ('" + name + "', '" + islandType + "', '" + record + "', '" +
                args + "')");
    }

    public String get(String name, int column) {
        try {
            return executeQuery("SELECT * FROM users WHERE name='" + name + "'").getString(column);
        } catch (SQLException | NullPointerException exception) {}

        return "";

    }

    public void remove(String name) {
        executeQuery("DELETE FROM users WHERE name=" + name);
    }

    private ResultSet executeQuery(String query) {

        try {
            return statement.executeQuery(query);
        } catch (SQLException exception) {

        }

        return null;
    }

}
