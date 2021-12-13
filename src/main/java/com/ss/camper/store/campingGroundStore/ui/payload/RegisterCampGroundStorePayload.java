package com.ss.camper.store.campingGroundStore.ui.payload;

import com.ss.camper.store.campingGroundStore.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.domain.Address;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
            return new CampGroundStoreDTO(
                    null,
                    userId,
                    storeName,
                    new Address(zipCode, defaultAddress, detailAddress, latitude, longitude),
                    tel,
                    homepageUrl,
                    reservationUrl,
                    introduction,
                    tags
            );
        }
    }


}
