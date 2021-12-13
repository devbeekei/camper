package com.ss.camper.store.campingGroundStore.infra;

import com.ss.camper.store.campingGroundStore.domain.CampGroundTag;
import com.ss.camper.store.campingGroundStore.domain.CampGroundTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampGroundTagJpaRepository extends JpaRepository<CampGroundTag, Long>, CampGroundTagRepository {
}
