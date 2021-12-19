package com.ss.camper.store.ui.payload;

import com.ss.camper.store.application.dto.StoreDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetStorePayload {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final StoreDTO store;
    }

}
