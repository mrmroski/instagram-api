package com.instagram.instagram.models.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class CreateUserCommand {

    @NotEmpty(message = "USERNAME_NOT_EMPTY")
    private String username;

    @JsonCreator //https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
    public CreateUserCommand(String username) {
        this.username = username;
    }
}
