package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.StoreTag;
import com.ss.camper.store.domain.StoreType;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@DiscriminatorValue("camp_ground")
@AllArgsConstructor
public class CampGroundTag extends StoreTag {

    public CampGroundTag(String title) {
        this.title = title;
    }

    public CampGroundTag(long id, String title) {
        this.id = id;
        this.storeType = StoreType.camp_ground;
        this.title = title;
    }

}

