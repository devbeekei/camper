package com.ss.camper.store.infra;

import com.ss.camper.store.domain.CampGroundStore;
import com.ss.camper.store.domain.CampGroundStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampGroundStoreJpaRepository extends JpaRepository<CampGroundStore, Long>, CampGroundStoreRepository {
}
