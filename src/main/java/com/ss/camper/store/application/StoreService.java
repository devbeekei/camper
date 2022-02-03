package com.ss.camper.store.application;

import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.application.exception.NotFoundStoreException;
import com.ss.camper.store.domain.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;
    private final StoreTagRepository storeTagRepository;
    private final StoreRepositorySupport storeRepositorySupport;

    @Transactional
    public StoreDTO registerStore(final long userId, final StoreDTO storeDTO) {
        final Store store = storeRepository.save(Store.builder()
                .userId(userId)
                .storeType(storeDTO.getStoreType())
                .storeName(storeDTO.getStoreName())
                .storeStatus(storeDTO.getStoreStatus())
                .address(storeDTO.getAddress())
                .tel(storeDTO.getTel())
                .homepageUrl(storeDTO.getHomepageUrl())
                .reservationUrl(storeDTO.getReservationUrl())
                .introduction(storeDTO.getIntroduction())
                .build());
        updateTags(store, new LinkedHashSet<>(storeDTO.getTags()));
        return modelMapper.map(store, StoreDTO.class);
    }

    @Transactional
    public StoreDTO modifyStore(final long userId, final long storeId, final StoreDTO storeDTO) {
        final Store store = storeRepository.findByUserIdAndId(userId, storeId).orElseThrow(NotFoundStoreException::new);
        store.updateInfo(
            storeDTO.getStoreStatus(),
            storeDTO.getStoreName(),
            storeDTO.getAddress(),
            storeDTO.getTel(),
            storeDTO.getHomepageUrl(),
            storeDTO.getReservationUrl(),
            storeDTO.getIntroduction()
        );
        updateTags(store, new LinkedHashSet<>(storeDTO.getTags()));
        return modelMapper.map(store, StoreDTO.class);
    }

    @Transactional
    public void deleteStore(long userId, long storeId) {
        final Store store = storeRepository.findByUserIdAndId(userId, storeId).orElseThrow(NotFoundStoreException::new);
        store.delete();
    }

    @Transactional(readOnly = true)
    public StoreDTO getStoreInfo(final long id) {
        final Store store = storeRepository.findByIdAndDeletedIsNull(id).orElse(null);
        return store == null ? null : modelMapper.map(store, StoreDTO.class);
    }

    @Transactional(readOnly = true)
    public PageDTO<StoreListDTO> getStoreListByUserId(final long userId, final int size, final int page) {
        final PagingRequest pagingRequest = new PagingRequest(size, page);
        final Page<StoreListDTO> storeList = storeRepositorySupport.getStoreListByUserId(userId, pagingRequest);
        return modelMapper.map(storeList, PageDTO.class);
    }

    private void updateTags(final Store store, final Set<StoreTagDTO> tagsDTO) {
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
