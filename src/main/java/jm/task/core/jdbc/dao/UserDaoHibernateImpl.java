package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sql = """
                CREATE TABLE IF NOT EXISTS userstable 
                (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
                name VARCHAR(50) NOT NULL, 
                last_name VARCHAR(50) NOT NULL, 
                age TINYINT NOT NULL)""";
            var query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS userstable";
            var query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println( "User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        Transaction transaction = null;
        List<User> users;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createQuery("delete FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}