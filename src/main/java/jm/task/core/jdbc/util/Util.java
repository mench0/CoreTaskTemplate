package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "users";
        String userName = "root";
        String password = "23jh4nk2m4ihjndmiun";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Подключение к драйверу");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        String connectionURL= "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useTimezone=true&serverTimezone=UTC";
        Connection connect = null;

        // соединеие с БД
        try {
            connect = DriverManager.getConnection(connectionURL, userName, password);
//            System.out.println("Соединение с БД установлено");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Внимание! Соединение с БД не установлено!");
        }
        return connect;
    }
}
