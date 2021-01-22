package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Леопольд", "Смирнов", (byte) 50);
        userService.saveUser("Дональд", "Трамп", (byte) 72);
        userService.saveUser("Борис", "Паук", (byte) 30);
        userService.saveUser("Михал", "Иваныч", (byte) 45);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
