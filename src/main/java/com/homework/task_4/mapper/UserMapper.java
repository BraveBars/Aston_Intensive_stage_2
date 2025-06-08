package com.homework.task_4.mapper;

import com.homework.task_4.dto.UserDto;
import com.homework.task_4.entity.UserEntity;

public class UserMapper {
    public static UserDto toDto(UserEntity e){
        UserDto dto = new UserDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        dto.setAge(e.getAge());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    public static UserEntity toEntity(UserDto dto){
        UserEntity e = new UserEntity();
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setAge(dto.getAge());
        return e;
    }

    public static void updateEntity(UserEntity e, UserDto dto){
        if (dto.getName() != null){
            e.setName(dto.getName());
        }
        if (dto.getEmail() != null){
            e.setEmail(dto.getEmail());
        }
        if (dto.getAge() != null){
            e.setAge(dto.getAge());
        }
    }
}
