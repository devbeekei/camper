package com.ss.camper.store.campGround.application;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;

public interface CampGroundStoreService {
    void register(CampGroundStoreDTO campGroundStoreDTO);
    CampGroundStoreDTO getInfo(long id);
}