package com.ss.camper.blog.ui;

import com.ss.camper.blog.application.BlogService;
import com.ss.camper.blog.application.dto.BlogInfoDTO;
import com.ss.camper.blog.ui.payload.ModifyBlogPayload;
import com.ss.camper.blog.ui.payload.RegisterBlogPayload;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.DefaultApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping(name = "블로그 정보 조회", value = "{blogId}")
    public DataApiResponse<BlogInfoDTO> getBlogInfo(@PathVariable long blogId) {
        final BlogInfoDTO blogInfoDTO = blogService.getBlogInfo(blogId);
        return new DataApiResponse<>(blogInfoDTO);
    }

    @PostMapping(name = "블로그 개설")
    public DefaultApiResponse registerBlog(@Valid @RequestBody RegisterBlogPayload.Request request) {
        final long userId = 1;
        blogService.registerBlog(userId, request.convertBlogDTO());
        return new DefaultApiResponse();
    }

    @PutMapping(name = "블로그 정보 수정")
    public DefaultApiResponse modifyBlog(@Valid @RequestBody ModifyBlogPayload.Request request) {
        final long userId = 1;
        blogService.modifyBlog(userId, request.convertBlogDTO());
        return new DefaultApiResponse();
    }

}
