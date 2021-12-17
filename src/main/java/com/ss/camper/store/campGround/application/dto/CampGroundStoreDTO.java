package com.ss.camper.store.campGround.application.dto;

import com.ss.camper.store.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
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
    private Set<CampGroundTagDTO> tags;
}
