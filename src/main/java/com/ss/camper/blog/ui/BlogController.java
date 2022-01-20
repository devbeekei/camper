package com.ss.camper.blog.ui;

import com.ss.camper.blog.application.BlogService;
import com.ss.camper.blog.ui.payload.RegisterBlogPayload;
import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping(name = "블로그 개설")
    public DefaultApiResponse registerStore(@Valid @RequestBody RegisterBlogPayload.Request request) {
        final long userId = 1;
        blogService.registerBlog(userId, request.convertBlogDTO());
        return new DefaultApiResponse();
    }

}
