package com.ss.camper.store.campGround.application;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.application.dto.CampGroundTagDTO;
import com.ss.camper.store.campGround.domain.CampGroundStore;
import com.ss.camper.store.campGround.domain.CampGroundStoreRepository;
import com.ss.camper.store.campGround.domain.CampGroundTag;
import com.ss.camper.store.campGround.domain.CampGroundTagRepository;
import com.ss.camper.store.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CampGroundStoreV1Test {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private CampGroundStoreRepository campGroundStoreRepository;

    @Mock
    private CampGroundTagRepository campGroundTagRepository;

    @InjectMocks
    private CampGroundStoreV1 campGroundStoreV1;

    @Test
    @DisplayName("캠핑장 등록")
    void register() {
        // Given
        final long userId = 1;
        CampGroundStoreDTO campGroundStoreDTO = CampGroundStoreDTO.builder()
            .storeName("하늘캠핑장")
            .address(Address.builder()
                .zipCode("111-111")
                .defaultAddress("서울시 양주시")
                .detailAddress("123-123")
                .latitude(124.23f)
                .longitude(111.34f)
                .build())
            .tel("010-1234-1234")
            .homepageUrl("https://campingman.com")
            .reservationUrl("https://campingman.com/reservation")
            .introduction("안녕하세요. 하늘 캠핑장입니다..")
            .tags(new LinkedHashSet<>(){{
                add(CampGroundTagDTO.builder().title("가족캠핑").build());
                add(CampGroundTagDTO.builder().title("연인캠핑").build());
                add(CampGroundTagDTO.builder().title("솔로캠핑").build());
            }})
            .build();
        CampGroundStore campGroundStore = CampGroundStore.builder()
            .id(1)
            .storeName(campGroundStoreDTO.getStoreName())
            .address(campGroundStoreDTO.getAddress())
            .tel(campGroundStoreDTO.getTel())
            .homepageUrl(campGroundStoreDTO.getHomepageUrl())
            .reservationUrl(campGroundStoreDTO.getReservationUrl())
            .introduction(campGroundStoreDTO.getIntroduction())
            .build();
        given(campGroundStoreRepository.save(any(CampGroundStore.class))).willReturn(campGroundStore);
        CampGroundTag campGroundTag1 = CampGroundTag.builder().id(1).title("가족캠핑").build();
        CampGroundTag campGroundTag2 = CampGroundTag.builder().id(2).title("연인캠핑").build();
        CampGroundTag campGroundTag3 = CampGroundTag.builder().id(3).title("솔로캠핑").build();
        given(campGroundTagRepository.findByTitle(anyString())).willReturn(Optional.empty());
        given(campGroundTagRepository.save(any(CampGroundTag.class))).willReturn(campGroundTag1, campGroundTag2, campGroundTag3);

        // When
        CampGroundStoreDTO result = campGroundStoreV1.register(userId, campGroundStoreDTO);
        LinkedHashSet<CampGroundTagDTO> resultTag = new LinkedHashSet<>(){{
            add(modelMapper.map(campGroundTag1, CampGroundTagDTO.class));
            add(modelMapper.map(campGroundTag2, CampGroundTagDTO.class));
            add(modelMapper.map(campGroundTag3, CampGroundTagDTO.class));
        }};

        // Then
        assertThat(result.getStoreName()).isEqualTo(campGroundStoreDTO.getStoreName());
        assertThat(result.getAddress()).isEqualTo(campGroundStoreDTO.getAddress());
        assertThat(result.getTel()).isEqualTo(campGroundStoreDTO.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(campGroundStoreDTO.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(campGroundStoreDTO.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(campGroundStoreDTO.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(campGroundStoreDTO.getReservationUrl());
        assertThat(result.getTags().size()).isEqualTo(resultTag.size());
    }

    @Test
    @DisplayName("캠핑장 정보 조회")
    void getInfo() {
        // Given
        final long storeId = 1;
        CampGroundStore campGroundStore = CampGroundStore.builder()
            .id(storeId)
            .storeName("하늘캠핑장")
            .address(Address.builder()
                .zipCode("111-111")
                .defaultAddress("서울시 양주시")
                .detailAddress("123-123")
                .latitude(124.23f)
                .longitude(111.34f)
                .build())
            .tel("010-1234-1234")
            .homepageUrl("https://campingman.com")
            .reservationUrl("https://campingman.com/reservation")
            .introduction("안녕하세요. 하늘 캠핑장입니다..")
            .tags(new LinkedHashSet<>(){{
                add(CampGroundTag.builder().id(1).title("가족캠핑").build());
                add(CampGroundTag.builder().id(2).title("연인캠핑").build());
                add(CampGroundTag.builder().id(3).title("솔로캠핑").build());
            }})
            .build();
        given(campGroundStoreRepository.findById(anyLong())).willReturn(Optional.ofNullable(campGroundStore));

        // When
        CampGroundStoreDTO result = campGroundStoreV1.getInfo(storeId);

        // Then
        assertThat(result.getStoreName()).isEqualTo(campGroundStore.getStoreName());
        assertThat(result.getAddress()).isEqualTo(campGroundStore.getAddress());
        assertThat(result.getTel()).isEqualTo(campGroundStore.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(campGroundStore.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(campGroundStore.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(campGroundStore.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(campGroundStore.getReservationUrl());
        assertThat(result.getTags().size()).isEqualTo(campGroundStore.getTags().size());
    }

    @Test
    @DisplayName("캠핑장 정보 조회 (존재하지 않는 캠핑장 정보)")
    void getInfo_empty() {
        // Given
        final long storeId = 1;
        given(campGroundStoreRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        CampGroundStoreDTO result = campGroundStoreV1.getInfo(storeId);

        // Then
        assertThat(result).isNull();
    }

}