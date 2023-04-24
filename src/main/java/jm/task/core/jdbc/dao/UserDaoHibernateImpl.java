package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (var session = Util.getSessionFactory().openSession()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS userstable 
                (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
                name VARCHAR(50) NOT NULL, 
                last_name VARCHAR(50) NOT NULL, 
                age TINYINT NOT NULL)""";
            transaction = session.beginTransaction();
            var query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (var session = Util.getSessionFactory().openSession()) {
            String sql = "DROP TABLE IF EXISTS userstable";
            transaction = session.beginTransaction();
            var query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println( "User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (var session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}