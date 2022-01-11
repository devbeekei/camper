package com.ss.camper.store.domain;

import com.ss.camper.store.domain.Store;

import java.util.Optional;

public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findById(long id);
}
