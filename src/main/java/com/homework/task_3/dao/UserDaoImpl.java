package com.homework.task_3.dao;

import com.homework.task_3.model.User;
import com.homework.task_3.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            logger.info("User created. ID: {}", user.getId());
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            logger.error("Error creating user", ex);
            throw new RuntimeException("Create failed", ex);
        }
    }

    @Override
    public User read(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            if (user != null) {
                logger.info("User found by ID {}: {}", id, user);
            } else {
                logger.warn("User not found by ID {}", id);
            }
            return user;
        } catch (Exception ex) {
            logger.error("Error finding user by ID {}", id, ex);
            throw new RuntimeException("Read failed", ex);
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            logger.info("Found {} users.", users.size());
            return users;
        } catch (Exception ex) {
            logger.error("Error finding all users.", ex);
            throw new RuntimeException("Read all failed", ex);
        }
    }

    @Override
    public void update(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            logger.info("User updated successfully: {}", user);
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            logger.error("Error updating user: {}", user, ex);
            throw new RuntimeException("Update failed", ex);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
                logger.info("User with ID {} deleted successfully.", id);
            } else {
                logger.warn("Attempt to delete non-existent user ID {}.", id);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            logger.error("Error deleting user with ID {}", id, ex);
            throw new RuntimeException("Delete failed", ex);
        }
    }
}
