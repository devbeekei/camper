package com.ss.camper.store.infra;

import com.ss.camper.store.domain.Store;
import com.ss.camper.store.domain.StoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<Store, Long>, StoreRepository {
}
