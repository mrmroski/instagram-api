package com.instagram.instagram.models.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class CreatePostCommand {

    @NotEmpty(message = "URL_NOT_EMPTY")
    private String url;
    @NotEmpty(message = "USER_ID_NOT_EMPTY")
    private int userId;

    @JsonCreator
    public CreatePostCommand(String url, int userId) {
        this.url = url;
        this.userId = userId;
    }
}
