package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.StoreTag;
import com.ss.camper.store.domain.StoreType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("camp_ground")
@AllArgsConstructor
public class CampGroundTag extends StoreTag {
}

