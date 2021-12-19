package com.ss.camper.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "default_address")
    private String defaultAddress;
    
    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

}
