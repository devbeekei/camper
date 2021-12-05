package com.ss.camper.store.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "default_address")
    private String defaultAddress;
    
    @Column(name = "detail_address")
    private String detailAddress;

}
