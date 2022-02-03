package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    OPEN("영업중"),
    CLOSE("폐업"),
    HIDE("숨김");

    private String name;

}
