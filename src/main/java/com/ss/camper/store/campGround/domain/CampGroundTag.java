package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.StoreTag;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("camp_ground")
public class CampGroundTag extends StoreTag {

    public CampGroundTag(String title) {
        this.title = title;
    }

}

