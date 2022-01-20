package com.ss.camper.blog.application.dto;

import lombok.*;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoDTO {
    private Long id;
    private String title;
    private String introduction;
    private Long userId;
    private String userEmail;
    private String userNickname;
}
