package com.ss.camper.store.ui.payload;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeleteStoreProfileImagesPayload {

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotNull
        @Size(min = 1)
        private Long[] fileIds;
    }

}
