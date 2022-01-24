package com.ss.camper.user.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.WithMockCustomUser;
import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.UserType;
import com.ss.camper.user.ui.payload.SignUpPayload;
import com.ss.camper.user.ui.payload.UpdateUserInfoPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.ss.camper.common.ApiDocumentAttributes.storeTypeAttribute;
import static com.ss.camper.common.ApiDocumentAttributes.userTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.store.StoreMock.*;
import static com.ss.camper.store.StoreMock.TAG_TITLE2;
import static com.ss.camper.user.UserMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
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
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
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

    @Test
    @WithMockCustomUser
    void 회원_정보_조회() throws Exception {
        // Given
        final UserDTO userDTO = initUserDTO(1L, UserType.CLIENT);
        given(userService.getUserInfo(anyLong())).willReturn(userDTO);

        // When
        final ResultActions result = mockMvc.perform(
                get("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("userType").type(JsonFieldType.STRING).description("회원 유형").attributes(userTypeAttribute()),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("회원 연락처").optional()
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 회원_정보_수정() throws Exception {
        // Given
        final UserDTO userDTO = initUserDTO(1L, UserType.BUSINESS);
        given(userService.updateUserInfo(anyLong(), any(UserDTO.class))).willReturn(userDTO);

        // When
        final UpdateUserInfoPayload.Request request = UpdateUserInfoPayload.Request.builder()
                .nickname("김캠퍼2")
                .phone("01022222222")
                .build();
        final ResultActions result = mockMvc.perform(
                put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/update-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처").optional()
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("userType").type(JsonFieldType.STRING).description("회원 유형").attributes(userTypeAttribute()),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("회원 연락처").optional()
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 회원_탈퇴() throws Exception {
        // Given
        willDoNothing().given(userService).withdrawUser(anyLong());

        // When
        final ResultActions result = mockMvc.perform(
                delete("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/withdraw",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }
}