package com.ss.camper.store.domain;

import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.HashSet;

import static com.ss.camper.store.StoreMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StoreRepositorySupportTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreRepositorySupport storeRepositorySupport;

    @Test
    @DisplayName("매장 목록 조회")
    @Transactional
    void getStoreListPage() {
        // Given
        Store savedStore1 = storeRepository.save(initStore(null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE1));
            add(initStoreTag(null, TAG_TITLE2));
        }}));
        Store savedStore2 = storeRepository.save(initStore(null, new HashSet<>(){{
            add(initStoreTag(null, TAG_TITLE3));
            add(initStoreTag(null, TAG_TITLE4));
        }}));

        // When
        final int size = 10;
        final int page = 1;
        PagingRequest pagingRequest = new PagingRequest(10, 1);
        Page<StoreListDTO> result = storeRepositorySupport.getStoreListPage(pagingRequest);

        // Then
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getNumber() + 1).isEqualTo(page);
        assertThat(result.getContent().get(0).getStoreName()).isEqualTo(savedStore2.getStoreName());
        assertThat(result.getContent().get(0).getStoreType()).isEqualTo(savedStore2.getStoreType());
        assertThat(result.getContent().get(0).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE3); add(TAG_TITLE4); }});
        assertThat(result.getContent().get(1).getStoreName()).isEqualTo(savedStore1.getStoreName());
        assertThat(result.getContent().get(1).getStoreType()).isEqualTo(savedStore1.getStoreType());
        assertThat(result.getContent().get(1).getTags()).isEqualTo(new HashSet<>(){{ add(TAG_TITLE1); add(TAG_TITLE2); }});
    }

}