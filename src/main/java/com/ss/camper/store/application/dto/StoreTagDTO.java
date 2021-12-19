package com.ss.camper.store.application.dto;

import com.ss.camper.store.domain.StoreType;
import lombok.*;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreTagDTO {
    private Long id;
    private StoreType storeType;
    private String title;
}
