package com.ss.camper.store.application;

import com.ss.camper.common.util.S3Util;
import com.ss.camper.store.domain.Store;
import com.ss.camper.store.domain.StoreProfileImage;
import com.ss.camper.store.domain.StoreRepository;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ss.camper.store.StoreMock.initStore;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StoreProfileImageServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private S3Util s3Util;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreProfileImageService storeProfileImageService;

    @Test
    void 프로필_이미지_등록() {
        final long userId = 1;
        final long storeId = 2;
        final Store store = initStore(userId, storeId, null);
        final List<UploadFileDTO> uploadFileDTOList = new ArrayList<>(){{
            add(UploadFileDTO.builder()
                    .originName("profileImage1.jpg")
                    .uploadName("upload_profileImage1.jpg")
                    .path("/upload/upload_profileImage1.jpg")
                    .fullPath("https://s3/upload/upload_profileImage1.jpg")
                    .ext("JPG")
                    .size(124215)
                    .build());
            add(UploadFileDTO.builder()
                    .originName("profileImage2.jpg")
                    .uploadName("upload_profileImage2.jpg")
                    .path("/upload/upload_profileImage2.jpg")
                    .fullPath("https://s3/upload/upload_profileImage2.jpg")
                    .ext("JPG")
                    .size(124215)
                    .build());
        }};
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.of(store));
        given(s3Util.upload(anyString(), anyList())).willReturn(uploadFileDTOList);

        final List<MultipartFile> multipartFileList = new ArrayList<>(){{
            add(new MockMultipartFile(
                    "files",
                    uploadFileDTOList.get(0).getOriginName(),
                    "image/jpg",
                    "uploadFile".getBytes()));
            add(new MockMultipartFile(
                    "files",
                    uploadFileDTOList.get(1).getOriginName(),
                    "image/jpg",
                    "uploadFile".getBytes()));
        }};
        storeProfileImageService.updateProfileImages(userId, userId, multipartFileList);

        assertThat(store.getProfileImages().get(0).getOriginName()).isEqualTo(multipartFileList.get(0).getOriginalFilename());
        assertThat(store.getProfileImages().get(1).getOriginName()).isEqualTo(multipartFileList.get(1).getOriginalFilename());
    }

    @Test
    void 프로필_이미지_삭제() {
        final long userId = 1;
        final long storeId = 2;
        final Store store = Store.builder()
                .id(storeId)
                .userId(userId)
                .profileImages(new ArrayList() {{
                    add(StoreProfileImage.builder()
                            .id(1L)
                            .originName("profileImage1.jpg")
                            .uploadName("upload_profileImage1.jpg")
                            .path("/upload/upload_profileImage1.jpg")
                            .fullPath("https://s3/upload/upload_profileImage1.jpg")
                            .ext("JPG")
                            .size(124215)
                            .build());
                    add(StoreProfileImage.builder()
                            .id(2L)
                            .originName("profileImage2.jpg")
                            .uploadName("upload_profileImage2.jpg")
                            .path("/upload/upload_profileImage2.jpg")
                            .fullPath("https://s3/upload/upload_profileImage2.jpg")
                            .ext("JPG")
                            .size(124215)
                            .build());
                }})
                .build();
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.of(store));

        storeProfileImageService.deleteProfileImages(userId, storeId, new Long[]{1L});

        assertThat(store.getProfileImages().size()).isEqualTo(1);
        assertThat(store.getProfileImages().get(0).getId()).isEqualTo(2);
    }

}