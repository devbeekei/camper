package com.ss.camper.blog.domain;

import javax.persistence.*;

@Entity
@Table(name = "post_tag", uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "post_tag_id")
    private long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

}
