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
@Entity(name = "User")
@Table(name = "users")
//, uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username")})
@Builder
public class User {

    @Id
//    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int Id;

    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User(LocalDate createdAt, String username){
        this.createdAt = createdAt;
        this.username = username;
    }

    public void addPost(Post post){
        if (!this.posts.contains(post)){
            this.posts.add(post);
            post.setUser(this);
        }
    }

    public void removePost(Post post){
        if (this.posts.contains(post)) {
            this.posts.remove(post);
            post.setUser(null);
        }
    }

}
