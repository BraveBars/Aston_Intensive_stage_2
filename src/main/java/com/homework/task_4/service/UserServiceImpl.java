package com.homework.task_4.service;

import com.homework.task_4.entity.UserEntity;
import com.homework.task_4.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.homework.task_4.dto.UserDto;
import com.homework.task_4.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UserDto> findAll() {
        return repo.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        return repo.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDto create(UserDto dto) {
        UserEntity e = UserMapper.toEntity(dto);
        return UserMapper.toDto(repo.save(e));
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        UserEntity e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserMapper.updateEntity(e, dto);
        return UserMapper.toDto(repo.save(e));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
