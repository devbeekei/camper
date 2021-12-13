package com.ss.camper.store.campingGroundStore.infra;

import com.ss.camper.store.campingGroundStore.domain.CampGroundStore;
import com.ss.camper.store.campingGroundStore.domain.CampGroundStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampGroundStoreJpaRepository extends JpaRepository<CampGroundStore, Long>, CampGroundStoreRepository {
}
