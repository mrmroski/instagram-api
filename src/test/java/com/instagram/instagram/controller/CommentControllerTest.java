package com.instagram.instagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.instagram.models.Comment;
import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreateCommentCommand;
import com.instagram.instagram.models.command.CreatePostCommand;
import com.instagram.instagram.models.command.CreateUserCommand;
import com.instagram.instagram.models.command.EditCommentCommand;
import com.instagram.instagram.repo.CommentRepository;
import com.instagram.instagram.repo.PostRepository;
import com.instagram.instagram.repo.UserRepository;
import net.bytebuddy.asm.Advice;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
class CommentControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void itShouldDeleteComment() throws Exception {
        //...creating User
        User user = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user);

        //...creating Post
        Post post = new Post(1, LocalDate.now(), "http://funny.pic", user, null);
        postRepository.save(post);

        //...creating Comment
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(
                "LOL U DUMB", user.getId(), post.getId());
        String requestJsonComment = objectMapper.writeValueAsString(createCommentCommand);

        String responseJsonComment = postman.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonComment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("LOL U DUMB"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Comment resultComment = objectMapper.readValue(responseJsonComment, Comment.class);

        postman.perform(delete("/api/v1/comments/" + resultComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonComment))
                .andExpect(status().isNoContent());
    }

    @Test
    void itShouldGetComment() throws Exception {
        //...creating User
        User user = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user);

        //...creating Post
        Post post = new Post(1, LocalDate.now(), "http://funny.pic", user, null);
        postRepository.save(post);

        //...creating Comment
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(
                "LOL U DUMB", user.getId(), post.getId());
        String requestJsonComment = objectMapper.writeValueAsString(createCommentCommand);

        String responseJsonComment = postman.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonComment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("LOL U DUMB"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Comment resultComment = objectMapper.readValue(responseJsonComment, Comment.class);

        postman.perform(get("/api/v1/comments/" + resultComment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("LOL U DUMB"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.postId").value(post.getId()));

    }

    @Test
    void itShouldEditComment() throws Exception {
        //...creating User
        User user = new User(1, "Alex", LocalDate.now(), null, null);
        userRepository.save(user);

        //...creating Post
        Post post = new Post(1, LocalDate.now(), "http://funny.pic", user, null);
        postRepository.save(post);

        //...creating Comment
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(
                "LOL U DUMB", user.getId(), post.getId());
        String requestJsonComment = objectMapper.writeValueAsString(createCommentCommand);

        String responseJsonComment = postman.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonComment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("LOL U DUMB"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Comment resultComment = objectMapper.readValue(responseJsonComment, Comment.class);

        //...creating EditCommentCommand
        EditCommentCommand editCommentCommand = new EditCommentCommand("SRY SNOWFLOKAE", resultComment.getId());
        String requestJsonEdit = objectMapper.writeValueAsString(editCommentCommand);

        postman.perform(patch("/api/v1/comments/" + resultComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonEdit))
                .andExpect(status().isOk());

        postman.perform(get("/api/v1/comments/" + resultComment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("SRY SNOWFLOKAE"))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andExpect(jsonPath("$.editedAt").value(LocalDate.now()));
        //TODO NOT WORKING

    }
}