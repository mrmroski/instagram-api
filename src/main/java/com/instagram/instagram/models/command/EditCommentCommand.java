package com.instagram.instagram.models.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class EditCommentCommand {
    @NotEmpty(message = "CONTENT_NOT_EMPTY")
    private String content;
    private int postId;

    @JsonCreator
    public EditCommentCommand(String content, int postId) {
        this.content = content;
        this.postId = postId;
    }
}
