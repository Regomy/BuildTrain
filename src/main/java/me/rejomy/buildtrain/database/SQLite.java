package me.rejomy.buildtrain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends DataBase {

    public SQLite() {

        try {
            Class.forName("org.sqlite.JDBC").newInstance();

            connection = getConnection();

            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (name TEXT PRIMARY KEY, " +
                    "islandType TEXT, " +
                    "record TEXT, " +
                    "arguments TEXT)");

        } catch (Exception ignored) {}

    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:plugins/BuildTrain/users.db");
    }

}
