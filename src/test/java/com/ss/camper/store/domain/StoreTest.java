package com.ss.camper.store.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;

import static com.ss.camper.store.StoreMockData.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StoreTest {

    @Test
    @DisplayName("매장 정보 업데이트")
    public void updateInfo() {
        // Given
        final Store store = initStore(1L, null);

        // When
        store.updateInfo(storeName, address, tel, homepageUrl, reservationUrl, introduction);

        // Then
        assertThat(store.getStoreName()).isEqualTo(storeName);
        assertThat(store.getAddress()).isEqualTo(address);
        assertThat(store.getTel()).isEqualTo(tel);
        assertThat(store.getHomepageUrl()).isEqualTo(homepageUrl);
        assertThat(store.getReservationUrl()).isEqualTo(reservationUrl);
        assertThat(store.getIntroduction()).isEqualTo(introduction);
    }

    @Test
    @DisplayName("매장 태그 업데이트")
    public void updateTags() {
        // Given
        final Store store = initStore(1L, new LinkedHashSet<>(){{
            add(initStoreTag(1L, tagTitle1));
            add(initStoreTag(2L, tagTitle2));
            add(initStoreTag(3L, tagTitle3));
            add(initStoreTag(4L, tagTitle4));
        }});

        // When
        final LinkedHashSet<StoreTag> updateTags = new LinkedHashSet<>(){{
            add(initStoreTag(1L, tagTitle1));
            add(initStoreTag(2L, tagTitle2));
            add(initStoreTag(3L, tagTitle3));
        }};
        store.updateTags(updateTags);

        // Then
        assertThat(store.getTags().size()).isEqualTo(updateTags.size());
    }

}
