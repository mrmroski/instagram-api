package com.instagram.instagram.service;

import com.instagram.instagram.exception.ResourceNotFoundException;
import com.instagram.instagram.models.Comment;
import com.instagram.instagram.models.command.EditCommentCommand;
import com.instagram.instagram.repo.CommentRepository;
import com.instagram.instagram.repo.PostRepository;
import com.instagram.instagram.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment createComment(Comment comment) {
        if(!postRepository.existsById(comment.getPost().getId())) {
            throw new ResourceNotFoundException(String
                    .format("Post with id %s not found! Cannot add the post", comment.getPost().getId()));
        }
        if(!userRepository.existsById(comment.getUser().getId())) {
            throw new ResourceNotFoundException(String
                    .format("User with id %s not found! Cannot add the post", comment.getUser().getId()));
        }
        return commentRepository.save(comment);
    }

    public Comment findComment(int id) {
        return commentRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException(String.format("Comment with id %s not found!", id)));
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public void deleteCommentById(int id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String
                    .format("Comment with id %s not found!", id));
        }
    }

    public Comment editComment(EditCommentCommand command) {
        Comment comment = commentRepository.findById(command.getCommentId())
                .map(commentToEdit -> {
                    commentToEdit.setContent(command.getContent());
                    commentToEdit.setEditedAt(LocalDate.now());
                    return commentToEdit;
                }).orElseThrow(()
                        -> new ResourceNotFoundException(String.format("Comment with id %s not found!", command.getCommentId())));
        return commentRepository.saveAndFlush(comment);

    }


}
