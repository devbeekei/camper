package com.ss.camper.blog;

import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.domain.Blog;
import com.ss.camper.user.clientUser.domain.ClientUser;

public class BlogMock {

    public static final String TITLE = "Bonglog";
    public static final String INTRODUCTION = "공부와 업무를 정리한 공간";

    public static BlogDTO initBlogDTO(Long id) {
        return BlogDTO.builder().id(id).title(TITLE).introduction(INTRODUCTION).build();
    }

    public static Blog initBlog(Long userId, Long id) {
        return Blog.builder()
                .id(id)
                .user(ClientUser.builder().id(userId).build())
                .title(TITLE)
                .introduction(INTRODUCTION)
                .build();
    }

}
