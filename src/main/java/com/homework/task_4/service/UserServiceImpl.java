package com.homework.task_4.service;

import com.homework.task_4.dto.UserDto;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserReopsitory repo;

    @Override
    public List<UserDto> findAll() {
        return List.of();
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public UserDto create(UserDto dto) {
        return null;
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
