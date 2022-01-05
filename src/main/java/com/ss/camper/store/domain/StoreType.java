package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StoreType {

    campGround(1, "캠핑장", CampGroundStore.class),
    campSupply(2, "캠핑용품점", CampSupplyStore.class);

    private int id;
    private String name;
    private Class<?> domain;

}
