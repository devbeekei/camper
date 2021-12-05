package com.ss.camper.store.campingSupplyStore.domain;

import com.ss.camper.store.domain.StoreTag;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@SecondaryTable(name = "tag_of_store", pkJoinColumns = @PrimaryKeyJoinColumn(name = "store_tag_id"))
@DiscriminatorValue("camp_supply")
@Entity
public class CampSupplyTag extends StoreTag {
}
