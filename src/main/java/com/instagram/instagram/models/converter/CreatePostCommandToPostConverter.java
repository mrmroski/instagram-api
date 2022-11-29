package com.instagram.instagram.models.converter;

import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreatePostCommand;
import com.instagram.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreatePostCommandToPostConverter implements Converter<CreatePostCommand, Post> {

    private final UserService userService;

    @Override
    public Post convert(MappingContext<CreatePostCommand, Post> mappingContext) {
        CreatePostCommand command = mappingContext.getSource();
        User user = userService.findUserById(command.getUserId());
        return Post.builder()
                .user(user)
                .url(command.getUrl())
                .createdAt(LocalDate.now())
                .build();
    }
}
