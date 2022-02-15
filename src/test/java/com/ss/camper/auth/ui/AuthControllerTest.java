package com.ss.camper.auth.ui;

import com.ss.camper.auth.application.AuthCodeService;
import com.ss.camper.auth.application.AuthService;
import com.ss.camper.auth.ui.payload.GetTokenPayload;
import com.ss.camper.auth.ui.payload.SignInPayload;
import com.ss.camper.common.ControllerTest;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ss.camper.common.ApiDocumentAttributes.userTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.user.UserMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthCodeService authCodeService;

    @Test
    void 이메일_로그인() throws Exception {
        final String code = "1234567890_CODE";
        final String token = "1234567890_TOKEN";
        given(authService.signIn(anyString(), anyString())).willReturn(initUserInfoDTO(1L, UserType.CLIENT));
        given(authCodeService.issueAuthCode(any(UserInfoDTO.class))).willReturn(code);
        given(authCodeService.issueAuthToken(anyString())).willReturn(token);

        final SignInPayload.Request request = new SignInPayload.Request(EMAIL, PASSWORD);
        final ResultActions result = mockMvc.perform(
                post("/auth/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("auth/email-sign-in",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("user").type(JsonFieldType.OBJECT).description("로그인 회원 정보"),
                                        fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                        fieldWithPath("user.userType").type(JsonFieldType.STRING).description("회원 유형").attributes(userTypeAttribute()),
                                        fieldWithPath("user.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("user.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("user.phone").type(JsonFieldType.STRING).description("회원 연락처").optional(),
                                        fieldWithPath("user.withdrawal").type(JsonFieldType.BOOLEAN).description("회원 탈퇴여부"),
                                        fieldWithPath("user.profileImage").type(JsonFieldType.OBJECT).description("프로필 이미지").optional(),
                                        fieldWithPath("user.profileImage.id").type(JsonFieldType.NUMBER).description("파일 고유번호").optional(),
                                        fieldWithPath("user.profileImage.originName").type(JsonFieldType.STRING).description("원본 파일명").optional(),
                                        fieldWithPath("user.profileImage.uploadName").type(JsonFieldType.STRING).description("업로드 파일명").optional(),
                                        fieldWithPath("user.profileImage.fullPath").type(JsonFieldType.STRING).description("파일 전체 경로").optional(),
                                        fieldWithPath("user.profileImage.path").type(JsonFieldType.STRING).description("파일 경로").optional(),
                                        fieldWithPath("user.profileImage.size").type(JsonFieldType.NUMBER).description("파일 사이즈").optional(),
                                        fieldWithPath("user.profileImage.ext").type(JsonFieldType.STRING).description("파일 확장자").optional(),
                                        fieldWithPath("user.useAgreeTerms").type(JsonFieldType.OBJECT).description("이용 약관 정보").optional(),
                                        fieldWithPath("user.useAgreeTerms.id").type(JsonFieldType.NUMBER).description("이용 약관 정보 고유번호").optional(),
                                        fieldWithPath("user.useAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("이용 약관 정보 동의여부").optional(),
                                        fieldWithPath("user.useAgreeTerms.created").type(JsonFieldType.STRING).description("이용 약관 정보 생성일자").optional(),
                                        fieldWithPath("user.privacyPolicyAgreeTerms").type(JsonFieldType.OBJECT).description("개인정보 처리방침 정보").optional(),
                                        fieldWithPath("user.privacyPolicyAgreeTerms.id").type(JsonFieldType.NUMBER).description("개인정보 처리방침 고유번호").optional(),
                                        fieldWithPath("user.privacyPolicyAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("개인정보 처리방침 동의여부").optional(),
                                        fieldWithPath("user.privacyPolicyAgreeTerms.created").type(JsonFieldType.STRING).description("개인정보 처리방침 생성일자").optional(),
                                        fieldWithPath("user.created").type(JsonFieldType.STRING).description("회원 생성일자"),
                                        fieldWithPath("token").type(JsonFieldType.STRING).description("발급된 인증 토큰")
                                )
                        )
                ));
    }

    @Test
    void 인증_토큰_발급() throws Exception {
        final String token = "1234567890_TOKEN";
        given(authCodeService.issueAuthToken(anyString())).willReturn(token);

        final String code = "1234567890_CODE";
        final GetTokenPayload.Request request = new GetTokenPayload.Request(code);
        final ResultActions result = mockMvc.perform(
                post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("auth/issue-token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("인증 코드")
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("token").type(JsonFieldType.STRING).description("발급된 인증 토큰")
                                )
                        )
                ));
    }

}