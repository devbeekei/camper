package com.ss.camper.store.campGround.ui.payload;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.application.dto.CampGroundTagDTO;
import com.ss.camper.store.domain.Address;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RegisterCampGroundStorePayload {

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
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

        public CampGroundStoreDTO getCampGroundStoreDTO() {
            LinkedHashSet<CampGroundTagDTO> saveTags = null;
            if (this.tags != null && !this.tags.isEmpty()) {
                saveTags = new LinkedHashSet<>();
                for (String tag : this.tags) {
                    saveTags.add(CampGroundTagDTO.builder().title(tag).build());
                }
            }
            return CampGroundStoreDTO.builder()
                .storeName(storeName)
                .address(new Address(zipCode, defaultAddress, detailAddress, latitude, longitude))
                .tel(tel)
                .homepageUrl(homepageUrl)
                .reservationUrl(reservationUrl)
                .introduction(introduction)
                .tags(saveTags)
                .build();
        }
    }


}
