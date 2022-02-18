package com.ss.camper.store.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.StoreStatus;
import com.ss.camper.store.domain.StoreType;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import lombok.*;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;
    private StoreType storeType;
    private StoreStatus storeStatus;
    private String storeName;
    private Address address;
    private String tel;
    private String homepageUrl;
    private String reservationUrl;
    private String introduction;
    private Set<DayOfWeek> openingDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date openTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date closeTime;
    private Set<StoreTagDTO> tags;
    private List<UploadFileDTO> profileImages;
}
