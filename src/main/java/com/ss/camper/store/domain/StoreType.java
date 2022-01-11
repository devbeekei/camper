package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StoreType {

    CAMP_GROUND("캠핑장"),
    CAMP_SUPPLY("캠핑용품점");

    private String name;

}
