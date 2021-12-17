package com.ss.camper.store.campSupply.domain;

import com.ss.camper.store.domain.StoreTag;
import lombok.*;

import javax.persistence.*;


@Getter
@Entity
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("camp_supply")
public class CampSupplyTag extends StoreTag {

    public CampSupplyTag(String title) {
        this.title = title;
    }

}
