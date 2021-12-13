package com.ss.camper.store.campingSupplyStore.infra;

import com.ss.camper.store.campingSupplyStore.domain.CampSupplyStore;
import com.ss.camper.store.campingSupplyStore.domain.CampSupplyStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampSupplyStoreJpaRepository extends JpaRepository<CampSupplyStore, Long>, CampSupplyStoreRepository {
}
