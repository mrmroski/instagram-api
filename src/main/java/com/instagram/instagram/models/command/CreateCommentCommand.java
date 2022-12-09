package com.instagram.instagram.models.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class CreateCommentCommand {

    @NotEmpty(message = "CONTENT_NOT_EMPTY")
    private String content;
    @NotEmpty(message = "USER_ID_NOT_EMPTY")
    private int userId;
    @NotEmpty(message = "POST_ID_NOT_EMPTY")
    private int postId;

    @JsonCreator
    public CreateCommentCommand(String content, int userId, int postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }
}
