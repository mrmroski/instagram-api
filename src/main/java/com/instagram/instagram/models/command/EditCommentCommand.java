package com.instagram.instagram.models.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditCommentCommand {
    private String content;
}
