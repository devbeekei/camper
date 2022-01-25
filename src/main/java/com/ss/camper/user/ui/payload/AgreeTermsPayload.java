package com.ss.camper.user.ui.payload;

import com.ss.camper.user.domain.TermsType;
import lombok.*;

import javax.validation.constraints.NotNull;

public class AgreeTermsPayload {

    @ToString
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotNull
        private TermsType termsType;
        @NotNull
        private boolean agree;
    }

}
