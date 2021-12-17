package com.ss.camper.store.campGround.ui.payload;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetCampGroundStorePayload {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final CampGroundStoreDTO store;
    }

}
