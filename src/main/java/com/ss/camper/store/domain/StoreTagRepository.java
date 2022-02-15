package com.ss.camper.store.domain;

import java.util.Optional;

public interface StoreTagRepository {
    StoreTag save(StoreTag storeTag);
    Optional<StoreTag> findByStoreTypeAndTitle(StoreType storeType, String title);
    void deleteAll();
}
