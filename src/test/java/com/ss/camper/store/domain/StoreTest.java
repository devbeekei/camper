package com.ss.camper.store.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;

import static com.ss.camper.store.StoreMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StoreTest {

    @Test
    @DisplayName("매장 정보 업데이트")
    public void updateInfo() {
        // Given
        final Store store = initStore(1L, null);

        // When
        store.updateInfo(STORE_NAME, ADDRESS, TEL, HOMEPAGE_URL, RESERVATION_URL, INTRODUCTION);

        // Then
        assertThat(store.getStoreName()).isEqualTo(STORE_NAME);
        assertThat(store.getAddress()).isEqualTo(ADDRESS);
        assertThat(store.getTel()).isEqualTo(TEL);
        assertThat(store.getHomepageUrl()).isEqualTo(HOMEPAGE_URL);
        assertThat(store.getReservationUrl()).isEqualTo(RESERVATION_URL);
        assertThat(store.getIntroduction()).isEqualTo(INTRODUCTION);
    }

    @Test
    @DisplayName("매장 태그 업데이트")
    public void updateTags() {
        // Given
        final Store store = initStore(1L, new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
            add(initStoreTag(4L, TAG_TITLE4));
        }});

        // When
        final LinkedHashSet<StoreTag> updateTags = new LinkedHashSet<>(){{
            add(initStoreTag(1L, TAG_TITLE1));
            add(initStoreTag(2L, TAG_TITLE2));
            add(initStoreTag(3L, TAG_TITLE3));
        }};
        store.updateTags(updateTags);

        // Then
        assertThat(store.getTags().size()).isEqualTo(updateTags.size());
    }

}
