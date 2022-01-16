package com.ss.camper.store.domain;

import java.util.Optional;

public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findById(long id);
}
