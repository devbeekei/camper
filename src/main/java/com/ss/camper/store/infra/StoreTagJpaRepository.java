package com.ss.camper.store.infra;

import com.ss.camper.store.domain.StoreTag;
import com.ss.camper.store.domain.StoreTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreTagJpaRepository extends JpaRepository<StoreTag, Long>, StoreTagRepository {
}
