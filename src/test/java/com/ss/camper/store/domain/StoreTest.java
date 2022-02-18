package com.ss.camper.store.domain;

import com.ss.camper.uploadFile.dto.UploadFileDTO;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.ss.camper.store.StoreMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StoreTest {

    @Test
    public void 매장_정보_업데이트() {
        // Given
        final Store store = initStore(1L, 2L, null);

        // When
        store.updateInfo(STORE_STATUS, STORE_NAME, ADDRESS, TEL, HOMEPAGE_URL, RESERVATION_URL, INTRODUCTION, OPENING_DAYS, OPEN_TIME, CLOSE_TIME);

        // Then
        assertThat(store.getStoreStatus()).isEqualTo(STORE_STATUS);
        assertThat(store.getStoreName()).isEqualTo(STORE_NAME);
        assertThat(store.getAddress()).isEqualTo(ADDRESS);
        assertThat(store.getTel()).isEqualTo(TEL);
        assertThat(store.getHomepageUrl()).isEqualTo(HOMEPAGE_URL);
        assertThat(store.getReservationUrl()).isEqualTo(RESERVATION_URL);
        assertThat(store.getIntroduction()).isEqualTo(INTRODUCTION);
        assertThat(store.getOpeningDays()).isEqualTo(OPENING_DAYS);
        assertThat(store.getOpenTime()).isEqualTo(OPEN_TIME);
        assertThat(store.getCloseTime()).isEqualTo(CLOSE_TIME);
    }

    @Test
    public void 매장_태그_업데이트() {
        // Given
        final Store store = initStore(1L, 2L, new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
            add(initStoreTag(4L, TAG_TITLE4));
        }});

        // When
        final LinkedHashSet<StoreTag> updateTags = new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
        }};
        store.updateTags(updateTags);

        // Then
        assertThat(store.getTags().size()).isEqualTo(updateTags.size());
    }

    @Test
    public void 매장_삭제() {
        final Store store = initStore(1L, 2L, null);

        store.delete();

        assertThat(store.getDeleted()).isNotNull();
    }

    @Test
    public void 프로필_이미지_등록() {
        final List<UploadFileDTO> uploadFileDTOList = new ArrayList<>() {{
            add(UploadFileDTO.builder()
                    .originName("originFileName1.jpg")
                    .uploadName("uploadFileName1.jpg")
                    .fullPath("https://s3/upload/uploadFileName1.jpg")
                    .path("/upload/uploadFileName1.jpg")
                    .size(2035)
                    .ext("JPG")
                    .build());
            add(UploadFileDTO.builder()
                    .originName("originFileName2.jpg")
                    .uploadName("uploadFileName2.jpg")
                    .fullPath("https://s3/upload/uploadFileName2.jpg")
                    .path("/upload/uploadFileName2.jpg")
                    .size(2035)
                    .ext("JPG")
                    .build());
        }};
        final Store store = initStore(1L, 2L, null);
        store.updateProfileImages(uploadFileDTOList);

        assertThat(store.getProfileImages().size()).isEqualTo(uploadFileDTOList.size());
        assertThat(store.getProfileImages().get(0).getOriginName()).isEqualTo(uploadFileDTOList.get(0).getOriginName());
        assertThat(store.getProfileImages().get(0).getUploadName()).isEqualTo(uploadFileDTOList.get(0).getUploadName());
        assertThat(store.getProfileImages().get(0).getFullPath()).isEqualTo(uploadFileDTOList.get(0).getFullPath());
        assertThat(store.getProfileImages().get(0).getPath()).isEqualTo(uploadFileDTOList.get(0).getPath());
        assertThat(store.getProfileImages().get(0).getSize()).isEqualTo(uploadFileDTOList.get(0).getSize());
        assertThat(store.getProfileImages().get(0).getExt()).isEqualTo(uploadFileDTOList.get(0).getExt());
        assertThat(store.getProfileImages().get(1).getOriginName()).isEqualTo(uploadFileDTOList.get(1).getOriginName());
        assertThat(store.getProfileImages().get(1).getUploadName()).isEqualTo(uploadFileDTOList.get(1).getUploadName());
        assertThat(store.getProfileImages().get(1).getFullPath()).isEqualTo(uploadFileDTOList.get(1).getFullPath());
        assertThat(store.getProfileImages().get(1).getPath()).isEqualTo(uploadFileDTOList.get(1).getPath());
        assertThat(store.getProfileImages().get(1).getSize()).isEqualTo(uploadFileDTOList.get(1).getSize());
        assertThat(store.getProfileImages().get(1).getExt()).isEqualTo(uploadFileDTOList.get(1).getExt());
    }

    @Test
    public void 프로필_이미지_삭제() {
        final List<StoreProfileImage> profileImages = new ArrayList<>() {{
            add(StoreProfileImage.builder()
                    .id(1L)
                    .originName("originFileName1.jpg")
                    .uploadName("uploadFileName1.jpg")
                    .fullPath("https://s3/upload/uploadFileName1.jpg")
                    .path("/upload/uploadFileName1.jpg")
                    .size(2035)
                    .ext("JPG")
                    .build());
            add(StoreProfileImage.builder()
                    .id(2L)
                    .originName("originFileName2.jpg")
                    .uploadName("uploadFileName2.jpg")
                    .fullPath("https://s3/upload/uploadFileName2.jpg")
                    .path("/upload/uploadFileName2.jpg")
                    .size(2035)
                    .ext("JPG")
                    .build());
        }};
        final Store store = Store.builder()
                .id(1L)
                .userId(2L)
                .storeType(STORE_TYPE)
                .storeStatus(STORE_STATUS)
                .storeName(STORE_NAME)
                .address(ADDRESS)
                .tel(TEL)
                .homepageUrl(HOMEPAGE_URL)
                .reservationUrl(RESERVATION_URL)
                .introduction(INTRODUCTION)
                .profileImages(profileImages)
                .build();
        store.deleteProfileImages(new Long[] { 1L, 2L });

        assertThat(store.getProfileImages().size()).isEqualTo(0);
    }

}
