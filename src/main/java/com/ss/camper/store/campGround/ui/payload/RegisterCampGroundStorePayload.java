package com.ss.camper.store.campGround.ui.payload;

import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.application.dto.CampGroundTagDTO;
import com.ss.camper.store.domain.Address;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
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

        private Set<String> tags;

        public CampGroundStoreDTO getCampGroundStoreDTO(long userId) {
            HashSet<CampGroundTagDTO> saveTags = null;
            if (this.tags != null && !this.tags.isEmpty()) {
                saveTags = new HashSet<>();
                for (String tag : this.tags) {
                    saveTags.add(new CampGroundTagDTO(null, tag));
                }
            }
            return new CampGroundStoreDTO(
                    null,
                    userId,
                    storeName,
                    new Address(zipCode, defaultAddress, detailAddress, latitude, longitude),
                    tel,
                    homepageUrl,
                    reservationUrl,
                    introduction,
                    saveTags
            );
        }
    }


}
