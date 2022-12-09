package com.instagram.instagram.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Comment")
@Table(name = "comments")
@Builder
public class Comment {

    @Id
//    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = SEQUENCE, generator = "comment_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int Id;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    @Column(name = "edited_at", updatable = false)
    private LocalDate editedAt;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "comment_post_fk"))
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "comment_user_fk"))
    private User user;

    public Comment(LocalDate createdAt, String content){
        this.createdAt = createdAt;
        this.content = content;
    }
}
