package ru.mipt.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.ds.PGPoolingDataSource;
/**
 * Created by artem on 11.11.15.
 */
public class DataBaseHelper {

    static Connection currentConnection;

    private static Connection connectToDataBase() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("My DB");
        source.setServerName("178.62.140.149");
        source.setDatabaseName("arhangeldim");
        source.setUser("senthil");
        source.setPassword("ubuntu");
        source.setMaxConnections(10);

        Connection connection = null;
        try {
            connection = source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            return connection;
        }
    }

    public static Connection getConnection() {
        if (currentConnection == null) {
            try {
                currentConnection = connectToDataBase();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return currentConnection;
    }


}
