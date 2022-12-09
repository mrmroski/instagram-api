package com.instagram.instagram.models.converter;

import com.instagram.instagram.models.Post;
import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreatePostCommand;
import com.instagram.instagram.models.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateUserCommandToUserConverter implements Converter<CreateUserCommand, User> {

    @Override
    public User convert(MappingContext<CreateUserCommand, User> mappingContext) {
       CreateUserCommand command = mappingContext.getSource();
        return User.builder()
                .username(command.getUsername())
                .build();
    }
}
