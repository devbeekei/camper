package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreType {

    camp_ground(1, "캠핑장"),
    camp_supply(2, "캠핑용품점");

    private int id;
    private String name;

}
