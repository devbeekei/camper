package com.ss.camper.store.application;

import com.ss.camper.store.application.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO register(StoreDTO storeDTO);
    StoreDTO modify(long storeId, StoreDTO storeDTO);
    StoreDTO getInfo(long id);
    List<StoreDTO> getList();
}
