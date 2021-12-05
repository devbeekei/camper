package com.ss.camper.blog.domain;

import com.ss.camper.common.domain.DateRecord;
import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "comments_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private ClientUser user;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Embedded
    private DateRecord dateRecord;

}
