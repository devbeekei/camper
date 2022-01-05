package com.ss.camper.store.infra;

import com.ss.camper.store.domain.CampSupplyStore;
import com.ss.camper.store.domain.CampSupplyStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampSupplyStoreJpaRepository extends JpaRepository<CampSupplyStore, Long>, CampSupplyStoreRepository {
}
