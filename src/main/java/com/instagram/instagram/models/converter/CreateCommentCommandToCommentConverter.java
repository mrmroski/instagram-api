package com.instagram.instagram.models.converter;

import com.instagram.instagram.models.Comment;
import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreateCommentCommand;
import com.instagram.instagram.service.PostService;
import com.instagram.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateCommentCommandToCommentConverter implements Converter<CreateCommentCommand, Comment> {

    private final UserService userService;
    private final PostService postService;

    @Override
    public Comment convert(MappingContext<CreateCommentCommand, Comment> mappingContext) {
        CreateCommentCommand command = mappingContext.getSource();
        User user = userService.findUserById(command.getUserId());
        Post post = postService.findPostById(command.getPostId());
        return Comment.builder()
                .user(user)
                .post(post)
                .content(command.getContent())
                .createdAt(LocalDate.now())
                .build();
    }
}
