package com.homework.task_3.service;

import com.homework.task_3.model.User;
import java.util.List;

public interface UserService {
    void create(User user);

    User read(Long id);

    List<User> readAll();

    void update(User user);

    void delete(Long id);
}
