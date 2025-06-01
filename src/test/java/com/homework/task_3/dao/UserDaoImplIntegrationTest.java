package com.homework.task_3.dao;

import com.homework.task_3.model.User;
import com.homework.task_3.util.HibernateUtil;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDaoImplIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("test_db")
                    .withUsername("bestuser")
                    .withPassword("bestuser");

    private UserDao userDao;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());
        System.setProperty("hibernate.hbm2ddl.auto", "create");
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.shutdown();
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        clearDatabase();
    }

    private void clearDatabase() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    void createUser_ShouldPersistUser() {
        User user = new User("Alice", "alice@example.com", 30);
        userDao.create(user);
        assertNotNull(user.getId(), "User ID should not be null after creation");
    }

    @Test
    void readUser_ShouldReturnSavedUser() {
        User user = new User("Bob", "bob@example.com", 25);
        userDao.create(user);

        User found = userDao.read(user.getId());
        assertNotNull(found, "User should be found by ID");
        assertEquals(user.getEmail(), found.getEmail(), "Emails should match");
    }

    @Test
    void readAll_ShouldReturnAllUsers() {
        userDao.create(new User("User1", "user1@test.com", 20));
        userDao.create(new User("User2", "user2@test.com", 30));

        List<User> users = userDao.readAll();
        assertEquals(2, users.size(), "Should return two users");
    }

    @Test
    void updateUser_ShouldModifyExistingUser() {
        User user = new User("Charlie", "charlie@mail.com", 40);
        userDao.create(user);

        user.setName("Updated");
        userDao.update(user);

        User updated = userDao.read(user.getId());
        assertEquals("Updated", updated.getName(), "Name should be updated");
    }

    @Test
    void deleteUser_ShouldRemoveFromDatabase() {
        User user = new User("David", "david@domain.com", 50);
        userDao.create(user);

        userDao.delete(user.getId());
        assertNull(userDao.read(user.getId()), "User should be deleted");
    }
}