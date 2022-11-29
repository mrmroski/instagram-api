package com.instagram.instagram.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    public String content;
    public int userId;
    public int postId;
    public LocalDate createdAt;
}
