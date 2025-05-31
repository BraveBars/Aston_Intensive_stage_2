package com.homework.task_3.service;

import com.homework.task_3.dao.UserDao;
import com.homework.task_3.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getAge() == null) {
            throw new IllegalArgumentException("Имя, Email и возраст обязательны!");
        }
        userDao.create(user);
    }

    @Override
    public User read(Long id) {
        return userDao.read(id);
    }

    @Override
    public List<User> readAll() {
        return userDao.readAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
