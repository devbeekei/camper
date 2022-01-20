package com.ss.camper.blog.ui;

import com.ss.camper.blog.application.BlogService;
import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.ui.payload.RegisterBlogPayload;
import com.ss.camper.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ss.camper.blog.BlogMock.TITLE;
import static com.ss.camper.blog.BlogMock.INTRODUCTION;
import static com.ss.camper.blog.BlogMock.initBlogDTO;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
class BlogControllerTest extends ControllerTest {

    @MockBean
    private BlogService blogService;

    @Test
    void 블로그_개설() throws Exception {
        final long blogId = 1;
        final BlogDTO blogDTO = initBlogDTO(blogId);
        given(blogService.registerBlog(anyLong(), any(BlogDTO.class))).willReturn(blogDTO);

        final RegisterBlogPayload.Request request = RegisterBlogPayload.Request.builder()
                .title(TITLE)
                .introduction(INTRODUCTION)
                .build();
        final ResultActions result = mockMvc.perform(
                post("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("blog/register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("블로그 타이틀"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).description("블로그 설명")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

}