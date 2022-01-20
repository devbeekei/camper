package com.ss.camper.user.clientUser.domain;

import com.ss.camper.blog.domain.Blog;
import com.ss.camper.blog.domain.Post;
import com.ss.camper.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("client")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUser extends User {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Blog blog;

}
