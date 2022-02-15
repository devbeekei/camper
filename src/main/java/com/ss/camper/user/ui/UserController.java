package com.ss.camper.user.ui;

import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.util.SecurityUtil;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.application.UserAgreeTermsService;
import com.ss.camper.user.application.UserProfileImageService;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.ui.payload.SignUpPayload;
import com.ss.camper.user.ui.payload.UpdateUserInfoPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;
    private final UserAgreeTermsService userAgreeTermsService;
    private final UserProfileImageService userProfileImageService;

    @PostMapping(name = "사용자 회원 회원가입", value = "client")
    public DefaultApiResponse signUpClientUser(@Valid @RequestBody final SignUpPayload.Request request) {
        userService.signUpClientUser(request.convertUserInfoDTO(), request.getPassword(), request.getPasswordCheck());
        return new DefaultApiResponse();
    }

    @PostMapping(name = "사업자 회원 회원가입", value = "business")
    public DefaultApiResponse signUpBusinessUser(@Valid @RequestBody final SignUpPayload.Request request) {
        userService.signUpBusinessUser(request.convertUserInfoDTO(), request.getPassword(), request.getPasswordCheck());
        return new DefaultApiResponse();
    }

    @GetMapping(name = "회원 정보 조회")
    public DataApiResponse<UserInfoDTO> getUserInfo() {
        final long userId = SecurityUtil.getUserId();
        final UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        return new DataApiResponse<>(userInfoDTO);
    }

    @PutMapping(name = "회원 정보 수정")
    public DataApiResponse<UserInfoDTO> updateUserInfo(@Valid @RequestBody final UpdateUserInfoPayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        final UserInfoDTO userInfoDTO = userService.updateUserInfo(userId, request.convertUserInfoDTO());
        return new DataApiResponse<>(userInfoDTO);
    }

    @DeleteMapping(name = "회원 탈퇴")
    public DefaultApiResponse withdrawUser() {
        final long userId = SecurityUtil.getUserId();
        userService.withdrawUser(userId);
        return new DefaultApiResponse();
    }

    @PostMapping(name = "약관 동의", value = "agree-terms")
    public DefaultApiResponse agreeTerms(@Valid @RequestBody @NotNull final Map<TermsType, Boolean> request) {
        final long userId = SecurityUtil.getUserId();
        userAgreeTermsService.agreeTerms(userId, request);
        return new DefaultApiResponse();
    }

    @PostMapping(name = "프로필 이미지 등록", value = "profile-image")
    public DefaultApiResponse updateProfileImage(final @RequestPart(value="file") MultipartFile multipartFile) {
        final long userId = SecurityUtil.getUserId();
        userProfileImageService.updateProfileImage(userId, multipartFile);
        return new DefaultApiResponse();
    }

    @DeleteMapping(name = "프로필 이미지 삭제", value = "profile-image")
    public DefaultApiResponse updateProfileImage() {
        final long userId = SecurityUtil.getUserId();
        userProfileImageService.deleteProfileImage(userId);
        return new DefaultApiResponse();
    }

}
