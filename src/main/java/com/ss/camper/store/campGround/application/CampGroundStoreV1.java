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
    public void register(CampGroundStoreDTO campGroundStoreDTO) {
        CampGroundStore campGroundStore = campGroundStoreRepository.save(
                new CampGroundStore(
                        campGroundStoreDTO.getUserId(),
                        campGroundStoreDTO.getStoreName(),
                        campGroundStoreDTO.getAddress(),
                        campGroundStoreDTO.getTel(),
                        campGroundStoreDTO.getHomepageUrl(),
                        campGroundStoreDTO.getReservationUrl(),
                        campGroundStoreDTO.getIntroduction()
                )
        );
        Set<CampGroundTagDTO> tags = campGroundStoreDTO.getTags();
        if (tags != null && !tags.isEmpty()) {
            for (CampGroundTagDTO tag : tags) {
                Optional<CampGroundTag> campGroundTag = campGroundTagRepository.findByTitle(tag.getTitle());
                if (campGroundTag.isPresent()) {
                    campGroundStore.addTag(campGroundTag.get());
                } else {
                    campGroundStore.addTag(campGroundTagRepository.save(new CampGroundTag(tag.getTitle())));
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CampGroundStoreDTO getInfo(long id) {
        CampGroundStore campGroundStore = campGroundStoreRepository.findById(id).orElse(null);
        if (campGroundStore == null) {
            return null;
        } else {
            return modelMapper.map(campGroundStore, CampGroundStoreDTO.class);
        }
    }

}
