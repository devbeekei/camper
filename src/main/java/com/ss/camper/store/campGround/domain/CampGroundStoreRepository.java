package com.ss.camper.store.campGround.domain;

import java.util.Optional;

public interface CampGroundStoreRepository {
    CampGroundStore save(CampGroundStore campGroundStore);
    Optional<CampGroundStore> findById(long id);
}
