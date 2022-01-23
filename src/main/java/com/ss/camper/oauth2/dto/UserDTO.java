package com.ss.camper.oauth2.dto;

import com.ss.camper.user.domain.UserType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private UserType userType;
    private String email;
    private String nickname;
    private String phone;
}
