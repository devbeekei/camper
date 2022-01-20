package com.ss.camper.blog.domain;

import com.ss.camper.common.domain.DateRecord;
import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "blog")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "blog_id")
    private long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "introduction", length = 200)
    private String introduction;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private ClientUser user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog")
    private List<Post> posts;

    @Embedded
    private DateRecord dateRecord;

    public void updateInfo(String title, String introduction) {
        this.title = title;
        this.introduction = introduction;
    }

}
