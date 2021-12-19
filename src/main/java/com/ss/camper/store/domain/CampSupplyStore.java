package com.ss.camper.store.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("camp_supply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampSupplyStore extends Store {

}
