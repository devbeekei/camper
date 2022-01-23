package com.ss.camper.user.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.domain.UserType;
import com.ss.camper.user.ui.payload.SignUpPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.user.UserMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

    @MockBean
    private UserService userService;

    @Test
    void 사용자_회원_회원가입() throws Exception {
        // Given
        final UserDTO userDTO = initUserDTO(1L, UserType.CLIENT);
        given(userService.signUpClientUser(any(UserDTO.class), anyString(), anyString())).willReturn(userDTO);

        // When
        final SignUpPayload.Request request = SignUpPayload.Request.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .passwordCheck(PASSWORD)
            .nickname(NICKNAME)
            .phone(PHONE)
            .build();
        final ResultActions result = mockMvc.perform(
            post("/user/client")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("user/sign-up-client",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                    fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 확인"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    void 사업자_회원_회원가입() throws Exception {
        // Given
        final UserDTO userDTO = initUserDTO(1L, UserType.BUSINESS);
        given(userService.signUpBusinessUser(any(UserDTO.class), anyString(), anyString())).willReturn(userDTO);

        // When
        final SignUpPayload.Request request = SignUpPayload.Request.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .passwordCheck(PASSWORD)
            .nickname(NICKNAME)
            .phone(PHONE)
            .build();
        final ResultActions result = mockMvc.perform(
            post("/user/business")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("user/sign-up-business",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                    fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 확인"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }
}