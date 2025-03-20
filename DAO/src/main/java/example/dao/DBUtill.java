package example.dao;

import java.sql.*;

public class DBUtill {
    private static final String DEFAULT_DB_URL = "jdbc:postgresql://localhost:5432/postgres"; // Подключаемся к postgres
    private static final String TARGET_DB_URL = "jdbc:postgresql://localhost:5432/employees"; // Основная база
    private static final String DB_NAME = "employees";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DEFAULT_DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT 1 FROM pg_database WHERE datname = '" + DB_NAME + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) { // Если база не найдена - создаем
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME + " OWNER " + USER);
                System.out.println("Database '" + DB_NAME + "' created successfully.");
            } else {
                System.out.println("Database '" + DB_NAME + "' already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(TARGET_DB_URL, USER, PASSWORD);
    }
}
