package com.ss.camper.store.application;

import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.domain.*;
import com.ss.camper.store.application.exception.NotFoundStoreException;
import com.ss.camper.store.domain.StoreRepository;
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
        final long userId = 1;
        final long storeId = 2;
        final Store store = initStore(userId, storeId, null);
        given(storeRepository.save(any(Store.class))).willReturn(store);

        final StoreTag storeTag1 = initStoreTag(1L, TAG_TITLE1);
        final StoreTag storeTag2 = initStoreTag(2L, TAG_TITLE2);
        final StoreTag storeTag3 = initStoreTag(3L, TAG_TITLE3);
        given(storeTagRepository.findByStoreTypeAndTitle(any(StoreType.class), anyString())).willReturn(Optional.empty());
        given(storeTagRepository.save(any(StoreTag.class))).willReturn(storeTag1, storeTag2, storeTag3);

        final StoreDTO storeDTO = initStoreDTO(null, new HashSet<>(){{
            add(initStoreTagDTO(null, TAG_TITLE1));
            add(initStoreTagDTO(null, TAG_TITLE2));
            add(initStoreTagDTO(null, TAG_TITLE3));
        }});
        final StoreDTO result = storeService.registerStore(userId, storeDTO);

        assertThat(result.getId()).isEqualTo(store.getId());
        assertThat(result.getStoreType()).isEqualTo(storeDTO.getStoreType());
        assertThat(result.getStoreName()).isEqualTo(storeDTO.getStoreName());
        assertThat(result.getAddress()).isEqualTo(storeDTO.getAddress());
        assertThat(result.getTel()).isEqualTo(storeDTO.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(storeDTO.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(storeDTO.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(storeDTO.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(storeDTO.getReservationUrl());
        assertThat(result.getOpeningDays()).isEqualTo(storeDTO.getOpeningDays());
        assertThat(result.getOpenTime()).isEqualTo(storeDTO.getOpenTime());
        assertThat(result.getCloseTime()).isEqualTo(storeDTO.getCloseTime());
        assertThat(result.getTags().size()).isEqualTo(storeDTO.getTags().size());
    }

    @Test
    void 매장_정보_수정() {
        final long userId = 1;
        final long storeId = 2;
        final Store store = initStore(userId, storeId, null);
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.ofNullable(store));

        final StoreTag storeTag1 = initStoreTag(1L, TAG_TITLE1);
        final StoreTag storeTag2 = initStoreTag(2L, TAG_TITLE2);
        final StoreTag storeTag3 = initStoreTag(3L, TAG_TITLE3);
        given(storeTagRepository.findByStoreTypeAndTitle(any(StoreType.class), anyString())).willReturn(Optional.empty());
        given(storeTagRepository.save(any(StoreTag.class))).willReturn(storeTag1, storeTag2, storeTag3);

        final StoreDTO storeDTO = initStoreDTO(null, new HashSet<>(){{
            add(initStoreTagDTO(null, TAG_TITLE1));
            add(initStoreTagDTO(null, TAG_TITLE2));
            add(initStoreTagDTO(null, TAG_TITLE3));
        }});
        final StoreDTO result = storeService.modifyStore(userId, storeId, storeDTO);

        assertThat(result.getId()).isEqualTo(storeId);
        assertThat(result.getStoreType()).isEqualTo(storeDTO.getStoreType());
        assertThat(result.getStoreName()).isEqualTo(storeDTO.getStoreName());
        assertThat(result.getAddress()).isEqualTo(storeDTO.getAddress());
        assertThat(result.getTel()).isEqualTo(storeDTO.getTel());
        assertThat(result.getHomepageUrl()).isEqualTo(storeDTO.getHomepageUrl());
        assertThat(result.getReservationUrl()).isEqualTo(storeDTO.getReservationUrl());
        assertThat(result.getIntroduction()).isEqualTo(storeDTO.getIntroduction());
        assertThat(result.getReservationUrl()).isEqualTo(storeDTO.getReservationUrl());
        assertThat(result.getOpeningDays()).isEqualTo(storeDTO.getOpeningDays());
        assertThat(result.getOpenTime()).isEqualTo(storeDTO.getOpenTime());
        assertThat(result.getCloseTime()).isEqualTo(storeDTO.getCloseTime());
        assertThat(result.getTags().size()).isEqualTo(storeDTO.getTags().size());
    }

    @Test()
    void 존재하지_않는_매장_정보_수정() {
        // Given
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.empty());

        // When, Then
        final long userId = 1;
        final long storeId = 2;
        final StoreDTO storeDTO = initStoreDTO(null, null);
        assertThrows(NotFoundStoreException.class, () -> storeService.modifyStore(userId, storeId, storeDTO));
    }

    @Test
    void 매장_삭제() {
        // Given
        final long userId = 1;
        final long storeId = 2;
        final Store store = initStore(userId, storeId, null);
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.ofNullable(store));

        storeService.deleteStore(userId, storeId);
        
        assertThat(store != null ? store.getDeleted() : null).isNotNull();
    }

    @Test
    void 존재하지_않는_매장_삭제() {
        given(storeRepository.findByUserIdAndId(anyLong(), anyLong())).willReturn(Optional.empty());
        assertThrows(NotFoundStoreException.class, () -> storeService.deleteStore(anyLong(), anyLong()));
    }
    
    @Test
    void 매장_정보_조회() {
        // Given
        final long userId = 1;
        final long storeId = 2;
        final Store store = initStore(userId, storeId, new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
        }});
        given(storeRepository.findByIdAndDeletedIsNull(anyLong())).willReturn(Optional.ofNullable(store));

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
        given(storeRepository.findByIdAndDeletedIsNull(anyLong())).willReturn(Optional.empty());

        // When
        final long storeId = 1;
        final StoreDTO result = storeService.getStoreInfo(storeId);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void 회원_별_매장_목록_조회() {
        // Given
        List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        Page<StoreListDTO> storeListPage = new PageImpl<>(storeList);
        given(storeRepositorySupport.getStoreListByUserId(anyLong(), any(PagingRequest.class))).willReturn(storeListPage);

        // When
        final long userId = 1;
        final int size = 10;
        final int page = 1;
        final PageDTO<StoreListDTO> result = storeService.getStoreListByUserId(userId, size, page);

        // Then
        assertThat(result.getContent()).isEqualTo(storeListPage.getContent());
    }

    @Test
    void 매장_유형_별_매장_목록_조회() {
        // Given
        List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        Page<StoreListDTO> storeListPage = new PageImpl<>(storeList);
        given(storeRepositorySupport.getStoreListByType(any(StoreType.class), any(PagingRequest.class))).willReturn(storeListPage);

        // When
        final int size = 10;
        final int page = 1;
        final PageDTO<StoreListDTO> result = storeService.getStoreListByType(STORE_TYPE, size, page);

        // Then
        assertThat(result.getContent()).isEqualTo(storeListPage.getContent());
        assertThat(result.getContent().get(0).getStoreType()).isEqualTo(STORE_TYPE);
        assertThat(result.getContent().get(1).getStoreType()).isEqualTo(STORE_TYPE);
    }

}