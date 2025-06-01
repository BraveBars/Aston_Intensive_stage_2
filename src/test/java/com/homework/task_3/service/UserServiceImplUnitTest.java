package com.homework.task_3.service;

import com.homework.task_3.dao.UserDao;
import com.homework.task_3.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ShouldValidateInput() {
        User invalidUser1 = new User(null, "invalid@email.com", 30);
        assertThrows(IllegalArgumentException.class,
                () -> userService.create(invalidUser1),
                "Should throw for null name"
        );

        User invalidUser2 = new User("Valid", null, 30);
        assertThrows(IllegalArgumentException.class,
                () -> userService.create(invalidUser2),
                "Should throw for null email"
        );

        User invalidUser3 = new User("Valid", "valid@email.com", null);
        assertThrows(IllegalArgumentException.class,
                () -> userService.create(invalidUser3),
                "Should throw for null age"
        );

        verify(userDao, never()).create(any());
    }

    @Test
    void readUser_ShouldCallDao() {
        User expected = new User("John", "john@doe.com", 25);
        when(userDao.read(1L)).thenReturn(expected);

        User actual = userService.read(1L);
        assertEquals(expected.getEmail(), actual.getEmail(), "Emails should match");
        verify(userDao).read(1L);
    }

    @Test
    void readAll_ShouldReturnEmptyList() {
        when(userDao.readAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.readAll();
        assertTrue(result.isEmpty(), "Result should be empty");
        verify(userDao).readAll();
    }

    @Test
    void updateUser_ShouldPropagateCall() {
        User user = new User("Valid", "valid@update.com", 35);

        userService.update(user);
        verify(userDao).update(user);
    }

    @Test
    void deleteUser_ShouldCallDelete() {
        userService.delete(1L);
        verify(userDao).delete(1L);
    }
}