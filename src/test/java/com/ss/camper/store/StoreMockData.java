package com.ss.camper.store;

import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.*;

import java.util.LinkedHashSet;

public class StoreMockData {

    public static final StoreType storeType = StoreType.campGround;
    public static final String storeName = "하늘 캠핑장";
    public static final Address address = new Address("11516", "경기 양주시 백석읍 양주산성로737번길", "114", 111.11f, 222.22f);
    public static final String tel = "0507-1471-2078";
    public static final String homepageUrl = "https://cafe.naver.com/skycp2004";
    public static final String reservationUrl = "http://r.camperstory.com/resMain.hbb?reserve_path=RP&campseq=3658";
    public static final String introduction = "안녕하세요. 하늘 캠핑장입니다..";
    public static final String tagTitle1 = "가족캠핑";
    public static final String tagTitle2 = "연인캠핑";
    public static final String tagTitle3 = "솔로캠핑";
    public static final String tagTitle4 = "당일캠핑";

    public static StoreTagDTO initStoreTagDTO(Long id, StoreType storeType, String title) {
        return StoreTagDTO.builder().id(id).storeType(storeType).title(title).build();
    }

    public static StoreTag initStoreTag(long id, StoreType storeType, String title) {
        return StoreTag.builder().id(id).storeType(storeType).title(title).build();
    }

    public static StoreDTO initStoreDTO(Long id, StoreType storeType, LinkedHashSet<StoreTagDTO> tags) {
        return StoreDTO.builder()
            .id(id)
            .storeType(storeType)
            .storeName(storeName)
            .address(address)
            .tel(tel)
            .homepageUrl(homepageUrl)
            .reservationUrl(reservationUrl)
            .introduction(introduction)
            .tags(tags)
            .build();
    }

    public static CampGroundStore initStore(long id, StoreType storeType, LinkedHashSet<StoreTag> tags) {
        return CampGroundStore.builder()
            .id(id)
            .storeType(storeType)
            .storeName(storeName)
            .address(address)
            .tel(tel)
            .homepageUrl(homepageUrl)
            .reservationUrl(reservationUrl)
            .introduction(introduction)
            .tags(tags)
            .build();
    }

}
