package com.ss.camper.store.domain;

import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreListDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

import static com.ss.camper.store.StoreMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class StoreRepositorySupportTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreTagRepository storeTagRepository;

    @Autowired
    private StoreRepositorySupport storeRepositorySupport;

    @AfterEach
    void init() {
        storeRepository.deleteAll();
        storeTagRepository.deleteAll();
    }

    @Test
    void 회원_별_매장_목록_조회() {
        final long userId = 1;
        Store savedStore1 = storeRepository.save(initStore(userId, null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE1));
            add(initStoreTag(null, TAG_TITLE2));
        }}));
        Store savedStore2 = storeRepository.save(initStore(userId, null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE3));
            add(initStoreTag(null, TAG_TITLE4));
        }}));

        final int size = 10;
        final int page = 1;
        PagingRequest pagingRequest = new PagingRequest(10, 1);
        Page<StoreListDTO> result = storeRepositorySupport.getStoreListByUserId(userId, pagingRequest);

        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getNumber() + 1).isEqualTo(page);
        assertThat(result.getContent().get(0).getStoreName()).isEqualTo(savedStore2.getStoreName());
        assertThat(result.getContent().get(0).getStoreType()).isEqualTo(savedStore2.getStoreType());
        assertThat(result.getContent().get(0).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE3); add(TAG_TITLE4); }});
        assertThat(result.getContent().get(1).getStoreName()).isEqualTo(savedStore1.getStoreName());
        assertThat(result.getContent().get(1).getStoreType()).isEqualTo(savedStore1.getStoreType());
        assertThat(result.getContent().get(1).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE1); add(TAG_TITLE2); }});
    }

    @Test
    @Rollback
    void 매장_유형_별_매장_목록_조회() {
        // Given
        Store savedStore1 = storeRepository.save(initStore(1L, null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE1));
            add(initStoreTag(null, TAG_TITLE2));
        }}));
        Store savedStore2 = storeRepository.save(initStore(1L, null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE3));
            add(initStoreTag(null, TAG_TITLE4));
        }}));

        // When
        final int size = 10;
        final int page = 1;
        PagingRequest pagingRequest = new PagingRequest(10, 1);
        Page<StoreListDTO> result = storeRepositorySupport.getStoreListByType(STORE_TYPE, pagingRequest);

        // Then
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getNumber() + 1).isEqualTo(page);
        assertThat(result.getContent().get(0).getStoreName()).isEqualTo(savedStore2.getStoreName());
        assertThat(result.getContent().get(0).getStoreType()).isEqualTo(STORE_TYPE);
        assertThat(result.getContent().get(0).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE3); add(TAG_TITLE4); }});
        assertThat(result.getContent().get(1).getStoreName()).isEqualTo(savedStore1.getStoreName());
        assertThat(result.getContent().get(1).getStoreType()).isEqualTo(STORE_TYPE);
        assertThat(result.getContent().get(1).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE1); add(TAG_TITLE2); }});
    }

}