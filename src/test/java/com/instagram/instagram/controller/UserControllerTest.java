package com.instagram.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreateUserCommand;
import com.instagram.instagram.repo.CommentRepository;
import com.instagram.instagram.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
class UserControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    void itShouldSaveUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand("Tommy");
        String requestJson = objectMapper.writeValueAsString(createUserCommand);

        String responseJson = postman.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("Tommy"))
                .andExpect(jsonPath("$.createdAt").value(LocalDate.now().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User result = objectMapper.readValue(responseJson, User.class);

        User saved = userRepository.findById(result.getId()).get();
        Assertions.assertEquals("Tommy", saved.getUsername());
        Assertions.assertEquals(LocalDate.now(), saved.getCreatedAt());
    }

    @Test
    void itShouldGetUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand("Tommy");
        String requestJson = objectMapper.writeValueAsString(createUserCommand);

        String responseJson = postman.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User result = objectMapper.readValue(responseJson, User.class);

        postman.perform(get("/api/v1/users/" + result.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("Tommy"))
                .andExpect(jsonPath("$.createdAt").value(LocalDate.now().toString()));
    }

    @Test
    void itShoulDeleteUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand("Tommy");
        String requestJson = objectMapper.writeValueAsString(createUserCommand);

        String responseJson = postman.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User result = objectMapper.readValue(responseJson, User.class);

       postman.perform(delete("/api/v1/users/" + result.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(false, userRepository.existsById(result.getId()));
    }

    @Test
    void itShouldReturnAllUsers() throws Exception {
        //... creating User #1
        CreateUserCommand createUserCommand = new CreateUserCommand("TommyLee");
        String requestJson = objectMapper.writeValueAsString(createUserCommand);

        String responseJson = postman.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("TommyLee"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User resultUse1 = objectMapper.readValue(responseJson, User.class);

        //... creating User #2
        CreateUserCommand createUserCommand2 = new CreateUserCommand("Alex");
        String requestJson2 = objectMapper.writeValueAsString(createUserCommand2);

        String responseJson2 = postman.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("Alex"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User result2 = objectMapper.readValue(responseJson2, User.class);

        postman.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(resultUse1.getId()))
                .andExpect(jsonPath("$.content.[0].username").value("TommyLee"))
                .andExpect(jsonPath("$.content.[0].createdAt").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.content.[1].id").value(result2.getId()))
                .andExpect(jsonPath("$.content.[1].username").value("Alex"))
                .andExpect(jsonPath("$.content.[1].createdAt").value(LocalDate.now().toString()));


    }


}