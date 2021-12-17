package com.ss.camper.store.campGround.infra;

import com.ss.camper.store.campGround.domain.CampGroundStore;
import com.ss.camper.store.campGround.domain.CampGroundStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampGroundStoreJpaRepository extends JpaRepository<CampGroundStore, Long>, CampGroundStoreRepository {
}
