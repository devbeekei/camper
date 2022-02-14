package com.ss.camper.user.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.WithMockCustomUser;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.application.UserAgreeTermsService;
import com.ss.camper.user.application.UserProfileImageService;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.domain.UserType;
import com.ss.camper.user.ui.payload.SignUpPayload;
import com.ss.camper.user.ui.payload.UpdateUserInfoPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.ss.camper.common.ApiDocumentAttributes.userTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.user.UserMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserAgreeTermsService userAgreeTermsService;

    @MockBean
    private UserProfileImageService userProfileImageService;

    @Test
    void 사용자_회원_회원가입() throws Exception {
        // Given
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.CLIENT);
        given(userService.signUpClientUser(any(UserInfoDTO.class), anyString(), anyString())).willReturn(userInfoDTO);

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
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.BUSINESS);
        given(userService.signUpBusinessUser(any(UserInfoDTO.class), anyString(), anyString())).willReturn(userInfoDTO);

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
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.CLIENT);
        given(userService.getUserInfo(anyLong())).willReturn(userInfoDTO);

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
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("회원 연락처").optional(),
                                        fieldWithPath("withdrawal").type(JsonFieldType.BOOLEAN).description("회원 탈퇴여부"),
                                        fieldWithPath("profileImage").type(JsonFieldType.OBJECT).description("프로필 이미지 정보").optional(),
                                        fieldWithPath("profileImage.id").type(JsonFieldType.NUMBER).description("파일 고유번호"),
                                        fieldWithPath("profileImage.originName").type(JsonFieldType.STRING).description("원본 파일명"),
                                        fieldWithPath("profileImage.uploadName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("profileImage.fullPath").type(JsonFieldType.STRING).description("파일 전체 경로"),
                                        fieldWithPath("profileImage.path").type(JsonFieldType.STRING).description("파일 경로"),
                                        fieldWithPath("profileImage.size").type(JsonFieldType.NUMBER).description("파일 사이즈"),
                                        fieldWithPath("profileImage.ext").type(JsonFieldType.STRING).description("파일 확장자"),
                                        fieldWithPath("useAgreeTerms").type(JsonFieldType.OBJECT).description("이용 약관 정보").optional(),
                                        fieldWithPath("useAgreeTerms.id").type(JsonFieldType.NUMBER).description("이용 약관 정보 고유번호"),
                                        fieldWithPath("useAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("이용 약관 정보 동의여부"),
                                        fieldWithPath("useAgreeTerms.created").type(JsonFieldType.STRING).description("이용 약관 정보 생성일자"),
                                        fieldWithPath("privacyPolicyAgreeTerms").type(JsonFieldType.OBJECT).description("개인정보 처리방침 정보").optional(),
                                        fieldWithPath("privacyPolicyAgreeTerms.id").type(JsonFieldType.NUMBER).description("개인정보 처리방침 고유번호"),
                                        fieldWithPath("privacyPolicyAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("개인정보 처리방침 동의여부"),
                                        fieldWithPath("privacyPolicyAgreeTerms.created").type(JsonFieldType.STRING).description("개인정보 처리방침 생성일자"),
                                        fieldWithPath("created").type(JsonFieldType.STRING).description("회원 생성일자")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 회원_정보_수정() throws Exception {
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.BUSINESS);
        given(userService.updateUserInfo(anyLong(), any(UserInfoDTO.class))).willReturn(userInfoDTO);

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
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("회원 연락처").optional(),
                                        fieldWithPath("withdrawal").type(JsonFieldType.BOOLEAN).description("회원 탈퇴여부"),
                                        fieldWithPath("profileImage").type(JsonFieldType.OBJECT).description("프로필 이미지 정보").optional(),
                                        fieldWithPath("profileImage.id").type(JsonFieldType.NUMBER).description("파일 고유번호"),
                                        fieldWithPath("profileImage.originName").type(JsonFieldType.STRING).description("원본 파일명"),
                                        fieldWithPath("profileImage.uploadName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("profileImage.fullPath").type(JsonFieldType.STRING).description("파일 전체 경로"),
                                        fieldWithPath("profileImage.path").type(JsonFieldType.STRING).description("파일 경로"),
                                        fieldWithPath("profileImage.size").type(JsonFieldType.NUMBER).description("파일 사이즈"),
                                        fieldWithPath("profileImage.ext").type(JsonFieldType.STRING).description("파일 확장자"),
                                        fieldWithPath("useAgreeTerms").type(JsonFieldType.OBJECT).description("이용 약관 정보").optional(),
                                        fieldWithPath("useAgreeTerms.id").type(JsonFieldType.NUMBER).description("이용 약관 정보 고유번호"),
                                        fieldWithPath("useAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("이용 약관 정보 동의여부"),
                                        fieldWithPath("useAgreeTerms.created").type(JsonFieldType.STRING).description("이용 약관 정보 생성일자"),
                                        fieldWithPath("privacyPolicyAgreeTerms").type(JsonFieldType.OBJECT).description("개인정보 처리방침 정보").optional(),
                                        fieldWithPath("privacyPolicyAgreeTerms.id").type(JsonFieldType.NUMBER).description("개인정보 처리방침 고유번호"),
                                        fieldWithPath("privacyPolicyAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("개인정보 처리방침 동의여부"),
                                        fieldWithPath("privacyPolicyAgreeTerms.created").type(JsonFieldType.STRING).description("개인정보 처리방침 생성일자"),
                                        fieldWithPath("created").type(JsonFieldType.STRING).description("회원 생성일자")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 회원_탈퇴() throws Exception {
        willDoNothing().given(userService).withdrawUser(anyLong());

        final ResultActions result = mockMvc.perform(
                delete("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("user/withdraw",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 약관_동의() throws Exception {
        willDoNothing().given(userAgreeTermsService).agreeTerms(anyLong(), anyMap());

        final Map<TermsType, Boolean> request = new HashMap<>(){{ put(TermsType.USE, true); put(TermsType.PRIVACY_POLICY, true); }};
        final ResultActions result = mockMvc.perform(
            post("/user/agree-terms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
            .andDo(document("user/agree-terms",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath(String.valueOf(TermsType.USE)).type(JsonFieldType.BOOLEAN).description(TermsType.USE.getName() + " 동의 여부").optional(),
                    fieldWithPath(String.valueOf(TermsType.PRIVACY_POLICY)).type(JsonFieldType.BOOLEAN).description(TermsType.PRIVACY_POLICY.getName() + " 동의 여부").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    @WithMockCustomUser
    void 프로필_이미지_등록() throws Exception {
        final UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                .id(1L)
                .originName("profileImage.jpg")
                .uploadName("upload_profileImage.jpg")
                .path("/upload/upload_profileImage.jpg")
                .fullPath("https://s3/upload/upload_profileImage.jpg")
                .ext("JPG")
                .size(124215)
                .build();
        given(userProfileImageService.updateProfileImage(anyLong(), any(MultipartFile.class))).willReturn(uploadFileDTO);

        final MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                uploadFileDTO.getOriginName(),
                "image/jpg",
                "uploadFile".getBytes());
        final ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.fileUpload("/user/profile-image")
                        .file(multipartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/profile-image",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("file").description("첨부 이미지")
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("파일 고유번호"),
                                        fieldWithPath("originName").type(JsonFieldType.STRING).description("원본 파일명"),
                                        fieldWithPath("uploadName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("fullPath").type(JsonFieldType.STRING).description("파일 전체 경로"),
                                        fieldWithPath("path").type(JsonFieldType.STRING).description("파일 경로"),
                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("파일 사이즈"),
                                        fieldWithPath("ext").type(JsonFieldType.STRING).description("파일 확장자")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void 프로필_이미지_삭제() throws Exception {
        willDoNothing().given(userProfileImageService).deleteProfileImage(anyLong());

        final ResultActions result = mockMvc.perform(
                delete("/user/profile-image")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("user/profile-image-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

}