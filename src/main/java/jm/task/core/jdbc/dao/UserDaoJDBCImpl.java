package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection conn = null;


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try(Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            try {
                statement.execute("CREATE TABLE IF NOT EXISTS user (" +
                        "id INT NOT NULL auto_increment PRIMARY KEY, \n" +
                        "name VARCHAR(20) not null, \n" +
                        "lastname VARCHAR(20) not null, \n" +
                        "age NUMERIC not null default 0 );");

                conn.commit();
            } catch (SQLException sqlException){
                sqlException.printStackTrace();
                conn.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try(Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            try {
                statement.execute("DROP TABLE IF EXISTS user;");
                conn.commit();
            } catch (SQLException sqlException){
                conn.rollback();
                sqlException.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    String sql_insert = "INSERT INTO user (name, lastName, age) values (?, ?, ?);";
    public void saveUser(String name, String lastName, byte age) {
        try(Connection conn = Util.getMySQLConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql_insert)) {

            conn.setAutoCommit(false);
            try {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);

                preparedStatement.executeUpdate();

                conn.commit();
            } catch (SQLException sqlException){
                conn.rollback();
                sqlException.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {

            conn.setAutoCommit(false);

            try {
                statement.execute("DELETE FROM user WHERE id=" + id + ";");

                conn.commit();
            } catch (SQLException sqlException){
                conn.rollback();
                sqlException.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        try(Connection conn = Util.getMySQLConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users.user;")) {

            conn.setAutoCommit(false);

            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

                    long id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String lastname = resultSet.getString("lastname");
                    int age = resultSet.getInt("age");

                    users.add(new User(id, name, lastname, (byte) age));
                    System.out.printf("%d) %s %s %d \n", id, name, lastname, age);
                }

                conn.commit();
            } catch (SQLException sqlException){
                conn.rollback();
                sqlException.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {

            conn.setAutoCommit(false);

            try {
                statement.execute("DELETE FROM user;");

                conn.commit();
            } catch (SQLException sqlException){
                conn.rollback();
                sqlException.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
