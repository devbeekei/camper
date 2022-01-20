package com.ss.camper.store.application;

import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.domain.*;
import com.ss.camper.store.application.exception.NotFoundStoreException;
import com.ss.camper.store.domain.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;

import static com.ss.camper.store.StoreMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreTagRepository storeTagRepository;

    @Mock
    private StoreRepositorySupport storeRepositorySupport;

    @InjectMocks
    private StoreService storeService;

    @Test
    void 매장_등록() {
        // Given
        final Store store = initStore(1L, null);
        given(storeRepository.save(any(Store.class))).willReturn(store);

        final StoreTag storeTag1 = initStoreTag(1L, TAG_TITLE1);
        final StoreTag storeTag2 = initStoreTag(2L, TAG_TITLE2);
        final StoreTag storeTag3 = initStoreTag(3L, TAG_TITLE3);
        given(storeTagRepository.findByStoreTypeAndTitle(any(StoreType.class), anyString())).willReturn(Optional.empty());
        given(storeTagRepository.save(any(StoreTag.class))).willReturn(storeTag1, storeTag2, storeTag3);

        // When
        final StoreDTO storeDTO = initStoreDTO(null, new HashSet<>(){{
            add(initStoreTagDTO(null, TAG_TITLE1));
            add(initStoreTagDTO(null, TAG_TITLE2));
            add(initStoreTagDTO(null, TAG_TITLE3));
        }});
        final StoreDTO result = storeService.registerStore(storeDTO);

        // Then
        assertThat(result.getId()).isEqualTo(store.getId());
        assertThat(result.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(result.getStoreName()).isEqualTo(store.getStoreName());
        assertThat(result.getAddress()).isEqualTo(store.getAddress());
        assertThat(result.getTel()).isEqualTo(store.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(store.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(store.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getTags().size()).isEqualTo(store.getTags().size());
    }

    @Test
    void 매장_정보_수정() {
        // Given
        final long storeId = 1;
        final Store store = initStore(storeId, null);
        given(storeRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(store));

        final StoreTag storeTag1 = initStoreTag(1L, TAG_TITLE1);
        final StoreTag storeTag2 = initStoreTag(2L, TAG_TITLE2);
        final StoreTag storeTag3 = initStoreTag(3L, TAG_TITLE3);
        given(storeTagRepository.findByStoreTypeAndTitle(any(StoreType.class), anyString())).willReturn(Optional.empty());
        given(storeTagRepository.save(any(StoreTag.class))).willReturn(storeTag1, storeTag2, storeTag3);

        // When
        final StoreDTO storeDTO = initStoreDTO(null, new HashSet<>(){{
            add(initStoreTagDTO(null, TAG_TITLE1));
            add(initStoreTagDTO(null, TAG_TITLE2));
            add(initStoreTagDTO(null, TAG_TITLE3));
        }});
        final StoreDTO result = storeService.modifyStore(storeId, storeDTO);

        // Then
        assertThat(result.getId()).isEqualTo(storeId);
        assertThat(result.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(result.getStoreName()).isEqualTo(store.getStoreName());
        assertThat(result.getAddress()).isEqualTo(store.getAddress());
        assertThat(result.getTel()).isEqualTo(store.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(store.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(store.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getTags().size()).isEqualTo(store.getTags().size());
    }

    @Test()
    void 존재하지_않는_매장_정보_수정() {
        // Given
        given(storeRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // When, Then
        final long storeId = 1;
        final StoreDTO storeDTO = initStoreDTO(null, null);
        assertThrows(NotFoundStoreException.class, () -> storeService.modifyStore(storeId, storeDTO));
    }

    @Test
    void 매장_정보_조회() {
        // Given
        final long storeId = 1;
        final Store store = initStore(storeId, new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
        }});
        given(storeRepository.findById(anyLong())).willReturn(Optional.ofNullable(store));

        // When
        final StoreDTO result = storeService.getStoreInfo(storeId);

        // Then
        assertThat(result.getId()).isEqualTo(storeId);
        assertThat(result.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(result.getStoreName()).isEqualTo(store.getStoreName());
        assertThat(result.getAddress()).isEqualTo(store.getAddress());
        assertThat(result.getTel()).isEqualTo(store.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(store.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(store.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(store.getReservationUrl());
        assertThat(result.getTags().size()).isEqualTo(store.getTags().size());
    }

    @Test
    void 존재하지_않는_매장_정보_조회() {
        // Given
        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        final long storeId = 1;
        final StoreDTO result = storeService.getStoreInfo(storeId);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void 매장_목록_조회() {
        // Given
        List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        Page<StoreListDTO> storeListPage = new PageImpl<>(storeList);
        given(storeRepositorySupport.getStoreListPage(any(PagingRequest.class))).willReturn(storeListPage);

        // When
        final int size = 10;
        final int page = 1;
        final PageDTO<StoreListDTO> result = storeService.getStoreListPage(size, page);

        // Then
        assertThat(result.getContent()).isEqualTo(storeListPage.getContent());
    }

}