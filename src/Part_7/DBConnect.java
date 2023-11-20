package Part_7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для соединения с БД
 */
public class DBConnect {
    // JDBC URL для подключения к MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/";
    //Имя базы данных
    public static final String BD_NAME = "test";
    // Пользователь БД
    private static final String USER = "root";
    // Пароль к БД
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL+BD_NAME, USER, PASSWORD);
    }
}
