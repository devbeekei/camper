package com.ss.camper.user.application.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileImageDTO {
    private Long id;
    private String originName;
    private String uploadName;
    private String fullPath;
    private String path;
}
