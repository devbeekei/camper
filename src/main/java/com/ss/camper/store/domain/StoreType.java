package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StoreType {

    camp_ground(1, "캠핑장", CampGroundStore.builder()),
    camp_supply(2, "캠핑용품점", CampGroundStore.builder());

    private int id;
    private String name;
    private Store.StoreBuilder<?,?> builder;

}
