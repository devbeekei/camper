package com.ss.camper.store;

import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.*;

import java.util.Set;

public class StoreMock {

    public static final StoreStatus STORE_STATUS = StoreStatus.OPEN;
    public static final StoreType STORE_TYPE = StoreType.CAMP_GROUND;
    public static final String STORE_NAME = "하늘 캠핑장";
    public static final Address ADDRESS = new Address("11516", "경기 양주시 백석읍 양주산성로737번길", "114", 111.11f, 222.22f);
    public static final String TEL = "0507-1471-2078";
    public static final String HOMEPAGE_URL = "https://cafe.naver.com/skycp2004";
    public static final String RESERVATION_URL = "http://r.camperstory.com/resMain.hbb?reserve_path=RP&campseq=3658";
    public static final String INTRODUCTION = "안녕하세요. 하늘 캠핑장입니다..";
    public static final String TAG_TITLE1 = "TEST_가족캠핑";
    public static final String TAG_TITLE2 = "TEST_연인캠핑";
    public static final String TAG_TITLE3 = "TEST_솔로캠핑";
    public static final String TAG_TITLE4 = "TEST_당일캠핑";

    public static StoreTag initStoreTag(Long id, String title) {
        return StoreTag.builder().id(id).storeType(STORE_TYPE).title(title).build();
    }

    public static StoreTagDTO initStoreTagDTO(Long id, String title) {
        return StoreTagDTO.builder().id(id).title(title).build();
    }

    public static Store initStore(Long userId, Long storeId, Set<StoreTag> tags) {
            return Store.builder()
                .id(storeId)
                .userId(userId)
                .storeType(STORE_TYPE)
                .storeStatus(STORE_STATUS)
                .storeName(STORE_NAME)
                .address(ADDRESS)
                .tel(TEL)
                .homepageUrl(HOMEPAGE_URL)
                .reservationUrl(RESERVATION_URL)
                .introduction(INTRODUCTION)
                .tags(tags)
                .build();
    }

    public static StoreDTO initStoreDTO(Long id, Set<StoreTagDTO> tags) {
        return StoreDTO.builder()
                .id(id)
                .storeType(STORE_TYPE)
                .storeStatus(STORE_STATUS)
                .storeName(STORE_NAME)
                .address(ADDRESS)
                .tel(TEL)
                .homepageUrl(HOMEPAGE_URL)
                .reservationUrl(RESERVATION_URL)
                .introduction(INTRODUCTION)
                .tags(tags)
                .build();
    }

    public static StoreListDTO initStoreListDTO(Long storeId, String[] tags) {
        return StoreListDTO.builder()
                .id(storeId)
                .storeType(STORE_TYPE)
                .storeStatus(STORE_STATUS)
                .storeName(STORE_NAME)
                .address(ADDRESS)
                .tel(TEL)
                .homepageUrl(HOMEPAGE_URL)
                .reservationUrl(RESERVATION_URL)
                .introduction(INTRODUCTION)
                .tags(String.join(",", tags))
                .build();
    }

}
