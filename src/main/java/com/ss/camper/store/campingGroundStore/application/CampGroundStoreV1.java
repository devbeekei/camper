package com.ss.camper.store.campingGroundStore.application;

import com.ss.camper.store.campingGroundStore.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campingGroundStore.domain.CampGroundStore;
import com.ss.camper.store.campingGroundStore.domain.CampGroundStoreRepository;
import com.ss.camper.store.campingGroundStore.domain.CampGroundTag;
import com.ss.camper.store.campingGroundStore.domain.CampGroundTagRepository;
import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CampGroundStoreV1 implements CampGroundStoreService {

    private final CampGroundStoreRepository campGroundStoreRepository;
    private final CampGroundTagRepository campGroundTagRepository;

    @Override
    public void register(CampGroundStoreDTO campGroundStoreDTO) {
        CampGroundStore saved = campGroundStoreRepository.save(CampGroundStore.builder()
                .userId(campGroundStoreDTO.getUserId())
                .storeName(campGroundStoreDTO.getStoreName())
                .address(campGroundStoreDTO.getAddress())
                .tel(campGroundStoreDTO.getTel())
                .homepageUrl(campGroundStoreDTO.getHomepageUrl())
                .reservationUrl(campGroundStoreDTO.getReservationUrl())
                .introduction(campGroundStoreDTO.getIntroduction())
                .build());

        Set<String> tags = campGroundStoreDTO.getTags();
        if (tags != null && !tags.isEmpty()) {
            for (String tag : tags) {
                Optional<CampGroundTag> campGroundTag = campGroundTagRepository.findByTitle(tag);
                if (campGroundTag.isPresent()) {
                    saved.addTag(campGroundTag.get());
                } else {
                    saved.addTag(campGroundTagRepository.save(CampGroundTag.builder()
                            .title(tag)
//                                    .storeId(saved.get)
                            .build()));
                }
            }
        }
    }

}
