package com.ss.camper.store.campSupply.infra;

import com.ss.camper.store.campSupply.domain.CampSupplyStore;
import com.ss.camper.store.campSupply.domain.CampSupplyStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampSupplyStoreJpaRepository extends JpaRepository<CampSupplyStore, Long>, CampSupplyStoreRepository {
}
