package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    /**
     * TEST
     * TEST
     * TEST
     *
     *
     * */
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Chris", "Cornell", (byte) 52);
        userService.saveUser("Kim", "Thayil", (byte) 53);
        userService.saveUser("Hiro", "Yamamoto", (byte) 54);
        userService.saveUser("Matt", "Cameron", (byte) 55);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
