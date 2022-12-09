package com.instagram.instagram.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_simple_view")
public class UserSimpleView {

    @Id
    private int Id;
    private String username;
    private LocalDate createdAt;
    private long amountOfPosts;
    private long amountOfComments;

}
