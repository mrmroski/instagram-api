package com.instagram.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreatePostCommand;
import com.instagram.instagram.models.command.CreateUserCommand;
import com.instagram.instagram.repo.PostRepository;
import com.instagram.instagram.repo.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
class PostControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void itShouldGetPost() throws Exception {
        //...creating User
        User user = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user);

        //... creating Post
        CreatePostCommand createPostCommand = new CreatePostCommand("http://funny.pic", user.getId());
        String requestJsonPost = objectMapper.writeValueAsString(createPostCommand);

        String responseJsonPost = postman.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonPost))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.url").value("http://funny.pic"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post resultPost = objectMapper.readValue(responseJsonPost, Post.class);

        postman.perform(get("/api/v1/posts/" + resultPost.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resultPost.getId()))
                .andExpect(jsonPath("$.url").value("http://funny.pic"))
                .andExpect(jsonPath("$.userId").value(user.getId()));


        Post saved = postRepository.findById(resultPost.getId()).get();
        Assertions.assertEquals("http://funny.pic", saved.getUrl());
        Assertions.assertEquals(user.getId(), saved.getUser().getId());
    }

    @Test
    void itShouldReturnAllPosts() throws Exception {
        //... creating User #1
        User user1 = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user1);

        //... creating User #2
        User user2 = new User(2, "Tommy", LocalDate.now().minusWeeks(78), null, null);
        userRepository.save(user2);

        //... creating Post #1
        CreatePostCommand createPostCommand1 = new CreatePostCommand("http://funny.pic", user1.getId());
        String requestJsonPost1 = objectMapper.writeValueAsString(createPostCommand1);

        String responseJsonPost1 = postman.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonPost1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.url").value("http://funny.pic"))
                .andExpect(jsonPath("$.userId").value(user1.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post resultPost1 = objectMapper.readValue(responseJsonPost1, Post.class);



        //... creating Post #2
        CreatePostCommand createPostCommand2 = new CreatePostCommand("http://sad.pic", user2.getId());
        String requestJsonPost2 = objectMapper.writeValueAsString(createPostCommand2);

        String responseJsonPost2 = postman.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonPost2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.url").value("http://sad.pic"))
                .andExpect(jsonPath("$.userId").value(user2.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post resultPost2 = objectMapper.readValue(responseJsonPost2, Post.class);

        //... getting all Posts
        postman.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(resultPost1.getId()))
                .andExpect(jsonPath("$.[0].url").value("http://funny.pic"))
                .andExpect(jsonPath("$.[0].userId").value(user1.getId()))
                .andExpect(jsonPath("$.[1].id").value(resultPost2.getId()))
                .andExpect(jsonPath("$.[1].url").value("http://sad.pic"))
                .andExpect(jsonPath("$.[1].userId").value(user2.getId()));

    }

    @Test
    void itShouldDeletePost() throws Exception {
        //... creating User
        User user1 = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user1);

        //... creating Post
        CreatePostCommand createPostCommand = new CreatePostCommand("http://funny.pic", user1.getId());
        String requestJsonPost = objectMapper.writeValueAsString(createPostCommand);

        String responseJsonPost = postman.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonPost))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.url").value("http://funny.pic"))
                .andExpect(jsonPath("$.userId").value(user1.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post resultPost = objectMapper.readValue(responseJsonPost, Post.class);

        postman.perform(delete("/api/v1/posts/" + resultPost.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(false, postRepository.existsById(resultPost.getId()));
    }
}