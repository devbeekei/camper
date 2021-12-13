package com.ss.camper.store.campingGroundStore.application.dto;

import com.ss.camper.store.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CampGroundStoreDTO {
    private Long id;
    private long userId;
    private String storeName;
    private Address address;
    private String tel;
    private String homepageUrl;
    private String reservationUrl;
    private String introduction;
    private Set<String> tags;
}
