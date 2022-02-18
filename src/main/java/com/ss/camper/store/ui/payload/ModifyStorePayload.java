package com.ss.camper.store.ui.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.*;

public class ModifyStorePayload {

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotNull
        private StoreStatus storeStatus;
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
        private Set<DayOfWeek> openingDays;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private Date openTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private Date closeTime;
        private Set<String> tags;

        public StoreDTO convertStoreDTO() {
            Set<StoreTagDTO> tags = new HashSet<>();
            if (this.tags != null && !this.tags.isEmpty()) {
                for (String tag : this.tags) {
                    tags.add(StoreTagDTO.builder().title(tag).build());
                }
            }
            return StoreDTO.builder()
                .storeStatus(storeStatus)
                .storeName(storeName)
                .address(new Address(zipCode, defaultAddress, detailAddress, latitude, longitude))
                .tel(tel)
                .homepageUrl(homepageUrl)
                .reservationUrl(reservationUrl)
                .introduction(introduction)
                .openingDays(openingDays)
                .openTime(openTime)
                .closeTime(closeTime)
                .tags(tags)
                .build();
        }
    }

}
