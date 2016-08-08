package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL         = "jdbc:mysql://localhost:3306/library?useSSL=false";
    private static final String USERNAME    = "root";
    private static final String PASSWORD    = "password";
    private Connection connection;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Nie mozna zaladowac sterownika do bazy danych");
            System.exit(0);
        }
    }

    public Connection openConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }

        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }

        connection = null;
    }

}
