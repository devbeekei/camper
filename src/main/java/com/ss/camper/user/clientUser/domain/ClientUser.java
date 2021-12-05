package com.ss.camper.user.clientUser.domain;

import com.ss.camper.blog.domain.Blog;
import com.ss.camper.blog.domain.Post;
import com.ss.camper.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("client")
@Entity
public class ClientUser extends User {

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private Blog blog;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts;

}
