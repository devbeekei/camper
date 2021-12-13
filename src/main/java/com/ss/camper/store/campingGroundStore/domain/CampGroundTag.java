package com.ss.camper.store.campingGroundStore.domain;

import com.ss.camper.store.domain.StoreTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@DiscriminatorValue("camp_ground")
@SecondaryTable(name = "tag_of_store", pkJoinColumns = @PrimaryKeyJoinColumn(name = "store_tag_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundTag extends StoreTag {

}
