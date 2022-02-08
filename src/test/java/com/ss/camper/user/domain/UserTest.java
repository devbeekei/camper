package com.ss.camper.user.domain;

import com.ss.camper.uploadFile.dto.UploadFileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ss.camper.user.UserMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Test
    public void 회원_정보_수정() {
        final String nickname = "김캠퍼2";
        final String phone = "01022222222";

        final ClientUser clientUser = initClientUser(1L);
        clientUser.updateInfo(nickname, phone);

        assertThat(clientUser.getNickname()).isEqualTo(nickname);
        assertThat(clientUser.getPhone()).isEqualTo(phone);
    }

    @Test
    public void 회원_탈퇴() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.withdraw();

        assertThat(clientUser.isWithdrawal()).isTrue();
    }

    @Test
    public void 약관_동의() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.agreeTerms(TermsType.USE, true);
        clientUser.agreeTerms(TermsType.PRIVACY_POLICY, true);

        assertThat(clientUser.getAgreeTermsHistories().size()).isEqualTo(2);
        assertThat(clientUser.getUseAgreeTerms().isAgree()).isTrue();
        assertThat(clientUser.getPrivacyPolicyAgreeTerms().isAgree()).isTrue();
    }

    @Test
    public void 프로필_이미지_삭제() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.clearProfileImage();

        assertThat(clientUser.getProfileImage()).isNull();
    }

    @Test
    public void 프로필_이미지_등록() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.updateProfileImage(UploadFileDTO.builder()
                .originName(PROFILE_ORIGIN_FILE_NAME)
                .uploadName(PROFILE_UPDATE_FILE_NAME)
                .fullPath(PROFILE_FULL_PATH)
                .path(PROFILE_PATH)
                .size(PROFILE_SIZE)
                .ext(PROFILE_EXT)
                .build());

        assertThat(clientUser.getProfileImage().getOriginName()).isEqualTo(PROFILE_ORIGIN_FILE_NAME);
        assertThat(clientUser.getProfileImage().getUploadName()).isEqualTo(PROFILE_UPDATE_FILE_NAME);
        assertThat(clientUser.getProfileImage().getFullPath()).isEqualTo(PROFILE_FULL_PATH);
        assertThat(clientUser.getProfileImage().getPath()).isEqualTo(PROFILE_PATH);
        assertThat(clientUser.getProfileImage().getSize()).isEqualTo(PROFILE_SIZE);
        assertThat(clientUser.getProfileImage().getExt()).isEqualTo(PROFILE_EXT);
    }

}
