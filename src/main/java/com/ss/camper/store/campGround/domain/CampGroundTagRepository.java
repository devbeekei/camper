package com.ss.camper.store.campGround.domain;

import java.util.Optional;

public interface CampGroundTagRepository {
    CampGroundTag save(CampGroundTag build);
    Optional<CampGroundTag> findByTitle(String title);
}
