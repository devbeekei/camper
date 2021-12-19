package com.ss.camper.store.ui.payload;

import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.LinkedHashSet;

public class RegisterStorePayload {

    @ToString
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotNull
        private StoreType storeType;

        @NotBlank
        private String storeName;

        @NotBlank
        private String zipCode;

        @NotBlank
        private String defaultAddress;

        private String detailAddress;

        @NotNull
        @Positive
        private Float latitude;

        @NotNull
        @Positive
        private Float longitude;

        @NotBlank
        private String tel;

        private String homepageUrl;

        private String reservationUrl;

        private String introduction;

        private LinkedHashSet<String> tags;

        public StoreDTO convertStoreDTO() {
            LinkedHashSet<StoreTagDTO> tags = null;
            if (this.tags != null && !this.tags.isEmpty()) {
                tags = new LinkedHashSet<>();
                for (String tag : this.tags) {
                    tags.add(StoreTagDTO.builder().title(tag).build());
                }
            }
            return StoreDTO.builder()
                .storeType(storeType)
                .storeName(storeName)
                .address(new Address(zipCode, defaultAddress, detailAddress, latitude, longitude))
                .tel(tel)
                .homepageUrl(homepageUrl)
                .reservationUrl(reservationUrl)
                .introduction(introduction)
                .tags(tags)
                .build();
        }
    }


}