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
@Table(name = "post_simple_view")
public class PostSimpleView {

    @Id
    private int id;
    private LocalDate createdAt;
    private String url;
    private int userId;
    private long amountOfComments;

}
