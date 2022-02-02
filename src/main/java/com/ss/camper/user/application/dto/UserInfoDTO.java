package com.ss.camper.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ss.camper.user.domain.UserType;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Long id;
    private UserType userType;
    private String email;
    private String nickname;
    private String phone;
    private Boolean withdrawal;
    private AgreeTermsHistoryDTO useAgreeTerms;
    private AgreeTermsHistoryDTO privacyPolicyAgreeTerms;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
}
