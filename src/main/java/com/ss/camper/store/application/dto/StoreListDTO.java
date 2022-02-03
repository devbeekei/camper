package com.ss.camper.store.application.dto;

import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreStatus;
import com.ss.camper.store.domain.StoreType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreListDTO {
    private Long id;
    private StoreType storeType;
    private StoreStatus storeStatus;
    private String storeName;
    private Address address;
    private String tel;
    private String homepageUrl;
    private String reservationUrl;
    private String introduction;
    private String tags;
    public Set<String> getTags() {
        if (StringUtils.isBlank(this.tags)) return new HashSet<>();
        return new HashSet<>(Arrays.asList(this.tags.split(",")));
    }
}
