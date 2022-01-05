package com.ss.camper.store.domain;

import com.ss.camper.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("campGround")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundStore extends Store {

}
