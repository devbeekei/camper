package com.ss.camper.blog.application.dto;

import lombok.*;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String introduction;
}
