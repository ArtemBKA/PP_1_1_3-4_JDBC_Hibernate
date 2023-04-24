package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS userstable 
            (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            NAME VARCHAR(50),
            LAST_NAME VARCHAR(50), 
            AGE TINYINT)
        """;
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS userstable";
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM usersTable";
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            var users = statement.executeQuery();
            while (users.next()) {
                list.add(new User(
                        users.getString("name")
                        , users.getString("last_name")
                        ,users.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO usersTable (NAME, LAST_NAME, AGE) VALUES (?, ?, ?)";
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                connection.commit();
                System.out.println("User с именем " + name + " добавлен в базу данных");
            } catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM usersTable WHERE ID = ?";
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                statement.setLong(1, id);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception ex) {
                connection.rollback();
                throw new RuntimeException(ex);
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE usersTable";
        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}