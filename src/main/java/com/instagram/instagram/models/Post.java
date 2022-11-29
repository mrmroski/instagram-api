package com.instagram.instagram.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table(name = "posts")
@Builder
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "post_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int Id;

    @Column(
            name = "created_at",
            updatable = false
    )
    private LocalDate createdAt;

    @Column(
            name = "url",
            nullable = false,
            updatable = false
    )
    private String url;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id", //Refers to 'int id' in User class
            foreignKey = @ForeignKey(
                    name = "user_post_fk"
            )
    )
    private User user;

    @OneToMany(
            mappedBy = "post",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    public Post(LocalDate createdAt, String url) {
        this.createdAt = createdAt;
        this.url = url;
    }

    public void addComment(Comment comment){
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setPost(this);
        }
    }

    public void removeComment(Comment comment){
        if (this.comments.contains(comment)) {
            this.comments.remove(comment);
            comment.setPost(null);
        }
    }
}
