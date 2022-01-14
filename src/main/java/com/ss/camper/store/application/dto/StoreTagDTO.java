package com.ss.camper.store.application.dto;

import lombok.*;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreTagDTO {
    private Long id;
    private String title;
}
