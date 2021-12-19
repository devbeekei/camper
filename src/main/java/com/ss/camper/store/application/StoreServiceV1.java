package com.ss.camper.store.application;

import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.*;
import com.ss.camper.store.application.exception.NotFoundStoreException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StoreServiceV1 implements StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;
    private final StoreTagRepository storeTagRepository;

    @Override
    @Transactional
    public StoreDTO register(StoreDTO storeDTO) {
        final StoreType storeType = storeDTO.getStoreType();
        final Store store = storeRepository.save(storeType.getBuilder()
                .storeName(storeDTO.getStoreName())
                .address(storeDTO.getAddress())
                .homepageUrl(storeDTO.getHomepageUrl())
                .reservationUrl(storeDTO.getReservationUrl())
                .introduction(storeDTO.getIntroduction())
                .build());
        updateTags(store, storeDTO.getTags());

        return modelMapper.map(store, StoreDTO.class);
    }

    @Override
    @Transactional
    public StoreDTO modify(long storeId, StoreDTO storeDTO) {
        final Store store = storeRepository.findById(storeId)
            .orElseThrow(NotFoundStoreException::new);
        store.updateInfo(
            storeDTO.getStoreName(),
            storeDTO.getAddress(),
            storeDTO.getTel(),
            storeDTO.getHomepageUrl(),
            storeDTO.getReservationUrl(),
            storeDTO.getIntroduction()
        );
        updateTags(store, storeDTO.getTags());
        return modelMapper.map(store, StoreDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreDTO getInfo(long id) {
        final Store store = storeRepository.findById(id).orElse(null);
        return store == null ? null : modelMapper.map(store, StoreDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getList() {
        return null;
    }

    private void updateTags(Store store, Set<StoreTagDTO> tagsDTO) {
        LinkedHashSet<StoreTag> tags = null;
        if (tagsDTO != null && !tagsDTO.isEmpty()) {
            tags = new LinkedHashSet<>();
            for (StoreTagDTO tagDTO : tagsDTO) {
                final Optional<StoreTag> storeTag = storeTagRepository.findByStoreTypeAndTitle(store.getStoreType(), tagDTO.getTitle());
                if (storeTag.isPresent()) {
                    tags.add(storeTag.get());
                } else {
                    tags.add(storeTagRepository.save(StoreTag.builder().storeType(store.getStoreType()).title(tagDTO.getTitle()).build()));
                }
            }
        }
        store.updateTags(tags);
    }

}
