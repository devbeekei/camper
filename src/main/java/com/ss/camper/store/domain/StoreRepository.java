package com.ss.camper.store.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findByUserIdAndId(long userId, long storeId);
    void deleteAll();

    @Query(value = "select s from Store s where s.id = :id")
    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Store> findByIdAndDeletedIsNull(@Param("id") long id);
}
