// src/test/java/com/homework/task_4/controller/UserControllerTest.java
package com.homework.task_4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.task_4.dto.UserDto;
import com.homework.task_4.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto sample;

    @BeforeEach
    void setUp() {
        sample = new UserDto();
        sample.setId(1L);
        sample.setName("Ivan Petrov");
        sample.setEmail("ivan.petrov@mail.com");
        sample.setAge(30);
        sample.setCreatedAt(LocalDateTime.of(2025, 8, 9, 10, 0));
    }

    @Test
    void testGetAll() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(List.of(sample));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ivan Petrov"));
    }

    @Test
    void testGetOne() throws Exception {
        Mockito.when(userService.findById(1L)).thenReturn(sample);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ivan.petrov@mail.com"));
    }

    @Test
    void testCreate() throws Exception {
        UserDto toCreate = new UserDto();
        toCreate.setName("Ivan");
        toCreate.setEmail("ivan@mail.com");
        toCreate.setAge(25);

        UserDto created = new UserDto();
        created.setId(2L);
        created.setName("Ivan");
        created.setEmail("ivan@mail.com");
        created.setAge(25);
        created.setCreatedAt(LocalDateTime.now());

        Mockito.when(userService.create(any(UserDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/2"))
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testUpdate() throws Exception {
        UserDto update = new UserDto();
        update.setName("Ivan Updated");
        update.setEmail("ivan.updated@mail.com");
        update.setAge(31);
        update.setId(1L);
        update.setCreatedAt(sample.getCreatedAt());

        Mockito.when(userService.update(eq(1L), any(UserDto.class))).thenReturn(update);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan Updated"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).delete(1L);
    }
}
