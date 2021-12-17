package com.ss.camper.store.campGround.infra;

import com.ss.camper.store.campGround.domain.CampGroundTag;
import com.ss.camper.store.campGround.domain.CampGroundTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampGroundTagJpaRepository extends JpaRepository<CampGroundTag, Long>, CampGroundTagRepository {
}
