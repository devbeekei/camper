package com.ss.camper.store.campGround.application;

import com.ss.camper.common.domain.DateRecord;
import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.application.dto.CampGroundTagDTO;
import com.ss.camper.store.campGround.domain.CampGroundStore;
import com.ss.camper.store.campGround.domain.CampGroundStoreRepository;
import com.ss.camper.store.campGround.domain.CampGroundTag;
import com.ss.camper.store.campGround.domain.CampGroundTagRepository;
import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreTag;
import com.ss.camper.store.domain.StoreType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class CampGroundStoreV1Test {

    @Mock
    private CampGroundStoreRepository campGroundStoreRepository;

    @Mock
    private CampGroundTagRepository campGroundTagRepository;

    @InjectMocks
    private CampGroundStoreV1 campGroundStoreV1;

    @Test
    @DisplayName("캠핑장 등록")
    void register() {
        // given
        final long userId = 1;
        CampGroundStore campGroundStore = new CampGroundStore(
                1,
                "하늘 캠핑장",
                new Address("111-11", "서울시 강남구 역삼동", "102호", 124.23f, 52.11f),
                "010-1234-1234",
                "https://campingman.com",
                "https://campingman.com/reservation",
                "안녕하세요. 하늘 캠핑장입니다...."
        );
        given(campGroundStoreRepository.save(any(CampGroundStore.class))).willReturn(campGroundStore);

        CampGroundTag campGroundTag1 = new CampGroundTag("가족캠핑");
        CampGroundTag campGroundTag2 = new CampGroundTag("연인캠핑");
        CampGroundTag campGroundTag3 = new CampGroundTag("솔로캠핑");
        given(campGroundTagRepository.findByTitle(anyString())).willReturn(Optional.empty());
        given(campGroundTagRepository.save(campGroundTag1)).willReturn(new CampGroundTag(1, campGroundTag1.getTitle()));
        given(campGroundTagRepository.save(campGroundTag2)).willReturn(new CampGroundTag(2, campGroundTag2.getTitle()));
        given(campGroundTagRepository.save(campGroundTag3)).willReturn(new CampGroundTag(3, campGroundTag3.getTitle()));

        CampGroundStoreDTO campGroundStoreDTO = new CampGroundStoreDTO(
                null,
        );
//        private Long id;
//        private String storeName;
//        private Address address;
//        private String tel;
//        private String homepageUrl;
//        private String reservationUrl;
//        private String introduction;
//        private Set<CampGroundTagDTO> tags;
        // When
        campGroundStoreV1.register(userId, campGroundStoreDTO);
    }

    @Test
    @DisplayName("캠핑장 정보 조회")
    void getInfo() {

    }

}