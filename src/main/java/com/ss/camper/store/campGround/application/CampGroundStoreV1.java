package com.ss.camper.store.campGround.application;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.application.dto.CampGroundTagDTO;
import com.ss.camper.store.campGround.domain.CampGroundStore;
import com.ss.camper.store.campGround.domain.CampGroundStoreRepository;
import com.ss.camper.store.campGround.domain.CampGroundTag;
import com.ss.camper.store.campGround.domain.CampGroundTagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CampGroundStoreV1 implements CampGroundStoreService {

    private final ModelMapper modelMapper;
    private final CampGroundStoreRepository campGroundStoreRepository;
    private final CampGroundTagRepository campGroundTagRepository;

    @Override
    @Transactional
    public CampGroundStoreDTO register(long userId, CampGroundStoreDTO campGroundStoreDTO) {
        final CampGroundStore campGroundStore = campGroundStoreRepository.save(
            CampGroundStore.builder()
                .storeName(campGroundStoreDTO.getStoreName())
                .address(campGroundStoreDTO.getAddress())
                .homepageUrl(campGroundStoreDTO.getHomepageUrl())
                .reservationUrl(campGroundStoreDTO.getReservationUrl())
                .introduction(campGroundStoreDTO.getIntroduction())
                .build());
        Set<CampGroundTagDTO> tags = campGroundStoreDTO.getTags();
        if (tags != null && !tags.isEmpty()) {
            for (CampGroundTagDTO tag : tags) {
                final Optional<CampGroundTag> campGroundTag = campGroundTagRepository.findByTitle(tag.getTitle());
                if (campGroundTag.isPresent()) {
                    campGroundStore.addTag(campGroundTag.get());
                } else {
                    campGroundStore.addTag(campGroundTagRepository.save(CampGroundTag.builder().title(tag.getTitle()).build()));
                }
            }
        }
        return modelMapper.map(campGroundStore, CampGroundStoreDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CampGroundStoreDTO getInfo(long id) {
        final CampGroundStore campGroundStore = campGroundStoreRepository.findById(id).orElse(null);
        return campGroundStore == null ? null : modelMapper.map(campGroundStore, CampGroundStoreDTO.class);
    }

}
