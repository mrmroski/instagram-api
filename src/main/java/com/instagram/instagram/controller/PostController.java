package com.instagram.instagram.controller;

import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.command.CreatePostCommand;
import com.instagram.instagram.models.dto.PostDto;
import com.instagram.instagram.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody CreatePostCommand command) {
        Post postToAdd = modelMapper.map(command, Post.class);
        Post createdPost = postService.createPost(postToAdd);
        return new ResponseEntity(modelMapper
                .map(createdPost, PostDto.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPost(@PathVariable("id") int id) {
        return new ResponseEntity(modelMapper
                .map((postService.findPostById(id)), PostDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAllPosts(){
        return new ResponseEntity(postService.findAllPosts()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePost (@PathVariable("id") int id){
        postService.deletePostById(id);
    }
}
