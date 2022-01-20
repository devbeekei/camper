package com.ss.camper.blog.ui;

import com.ss.camper.blog.application.BlogService;
import com.ss.camper.blog.application.dto.BlogDTO;
import com.ss.camper.blog.application.dto.BlogInfoDTO;
import com.ss.camper.blog.ui.payload.ModifyBlogPayload;
import com.ss.camper.blog.ui.payload.RegisterBlogPayload;
import com.ss.camper.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ss.camper.blog.BlogMock.*;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
class BlogControllerTest extends ControllerTest {

    @MockBean
    private BlogService blogService;

    @Test
    void 블로그_정보_조회() throws Exception {
        final long userId = 1;
        final long blogId = 1;
        final BlogInfoDTO blogInfoDTO = initBlogInfoDTO(userId, blogId);
        given(blogService.getBlogInfo(anyLong())).willReturn(blogInfoDTO);

        final ResultActions result = mockMvc.perform(
                get("/blog/{blogId}", blogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("blog/info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("blogId").description("블로그 고유번호")
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("블로그 고유번호"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("블로그 타이틀"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("블로그 소개"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("userEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("userNickname").type(JsonFieldType.STRING).description("회원 닉네임")
                                )
                        )
                ));
    }

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

    @Test
    void 블로그_정보_수정() throws Exception {
        final long blogId = 1;
        final BlogDTO blogDTO = initBlogDTO(blogId);
        given(blogService.modifyBlog(anyLong(), any(BlogDTO.class))).willReturn(blogDTO);

        final ModifyBlogPayload.Request request = ModifyBlogPayload.Request.builder()
                .title(TITLE)
                .introduction(INTRODUCTION)
                .build();
        final ResultActions result = mockMvc.perform(
                put("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("blog/modify",
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