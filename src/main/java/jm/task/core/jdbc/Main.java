package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("testName", "testLastName", (byte) 14);
        userService.removeUserById(1L);
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
