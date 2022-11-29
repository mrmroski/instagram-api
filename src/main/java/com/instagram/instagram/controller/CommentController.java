package com.instagram.instagram.controller;

import com.instagram.instagram.models.Comment;
import com.instagram.instagram.models.command.CreateCommentCommand;
import com.instagram.instagram.models.command.EditCommentCommand;
import com.instagram.instagram.models.dto.CommentDto;
import com.instagram.instagram.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody CreateCommentCommand command){
        Comment commentToAdd = modelMapper.map(command, Comment.class);
        Comment createdComment = commentService.createComment(commentToAdd);
        return new ResponseEntity(modelMapper.map(createdComment, CommentDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        return new ResponseEntity(commentService.findAllComments()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("id") int id){
        return new ResponseEntity(modelMapper.
                map(commentService.findComment(id), CommentDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") int id){
        commentService.deleteCommentById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> editCommentPartially(@PathVariable("id") int id, @RequestBody EditCommentCommand command){
        Comment editedComment = commentService.editComment(id, command);
        return new ResponseEntity(modelMapper.map(editedComment, CommentDto.class), HttpStatus.OK);
    }



}
