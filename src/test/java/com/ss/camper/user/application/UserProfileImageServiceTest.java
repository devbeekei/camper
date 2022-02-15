package com.ss.camper.user.application;

import com.ss.camper.common.util.S3Util;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.UserProfileImage;
import com.ss.camper.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.ss.camper.user.UserMock.initClientUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserProfileImageServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private S3Util s3Util;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileImageService userProfileImageService;

    @Test
    void 프로필_이미지_등록() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));
        final UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                .originName("profileImage.jpg")
                .uploadName("upload_profileImage.jpg")
                .path("/upload/upload_profileImage.jpg")
                .fullPath("https://s3/upload/upload_profileImage.jpg")
                .ext("JPG")
                .size(124215)
                .build();
        given(s3Util.upload(anyString(), any(MultipartFile.class))).willReturn(uploadFileDTO);

        final MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                uploadFileDTO.getOriginName(),
                "image/jpg",
                "uploadFile".getBytes());
        userProfileImageService.updateProfileImage(userId, multipartFile);

        final UserProfileImage userProfileImage = clientUser.getProfileImage();
        assertThat(userProfileImage.getOriginName()).isEqualTo(uploadFileDTO.getOriginName());
        assertThat(userProfileImage.getUploadName()).isEqualTo(uploadFileDTO.getUploadName());
        assertThat(userProfileImage.getPath()).isEqualTo(uploadFileDTO.getPath());
        assertThat(userProfileImage.getFullPath()).isEqualTo(uploadFileDTO.getFullPath());
        assertThat(userProfileImage.getExt()).isEqualTo(uploadFileDTO.getExt());
        assertThat(userProfileImage.getSize()).isEqualTo(uploadFileDTO.getSize());
    }

    @Test
    void 프로필_이미지_삭제() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        userProfileImageService.deleteProfileImage(userId);

        assertThat(clientUser.getProfileImage()).isNull();
    }

}