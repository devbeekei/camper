package com.ss.camper.store.application.dto;

import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreType;
import lombok.*;

import java.util.LinkedHashSet;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;
    private StoreType storeType;
    private String storeName;
    private Address address;
    private String tel;
    private String homepageUrl;
    private String reservationUrl;
    private String introduction;
    private LinkedHashSet<StoreTagDTO> tags;
}
