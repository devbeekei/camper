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
    public void 매장_정보_업데이트() {
        // Given
        final Store store = initStore(1L, 2L, null);

        // When
        store.updateInfo(STORE_STATUS, STORE_NAME, ADDRESS, TEL, HOMEPAGE_URL, RESERVATION_URL, INTRODUCTION);

        // Then
        assertThat(store.getStoreStatus()).isEqualTo(STORE_STATUS);
        assertThat(store.getStoreName()).isEqualTo(STORE_NAME);
        assertThat(store.getAddress()).isEqualTo(ADDRESS);
        assertThat(store.getTel()).isEqualTo(TEL);
        assertThat(store.getHomepageUrl()).isEqualTo(HOMEPAGE_URL);
        assertThat(store.getReservationUrl()).isEqualTo(RESERVATION_URL);
        assertThat(store.getIntroduction()).isEqualTo(INTRODUCTION);
    }

    @Test
    public void 매장_태그_업데이트() {
        // Given
        final Store store = initStore(1L, 2L, new LinkedHashSet<>(){{
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

    @Test
    public void 매장_삭제() {
        final Store store = initStore(1L, 2L, null);

        store.delete();

        assertThat(store.getDeleted()).isNotNull();
    }

}
