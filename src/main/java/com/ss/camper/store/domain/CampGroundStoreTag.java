package com.ss.camper.store.domain;

import com.ss.camper.store.domain.StoreTag;
import com.ss.camper.store.domain.StoreType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("camp_ground")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundStoreTag extends StoreTag {

}

