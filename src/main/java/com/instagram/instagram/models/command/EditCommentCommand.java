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
    private int commentId;

    @JsonCreator
    public EditCommentCommand(String content, int commentId) {
        this.content = content;
        this.commentId = commentId;
    }
}
