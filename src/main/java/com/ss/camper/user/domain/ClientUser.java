package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("CLIENT")
@AllArgsConstructor()
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUser extends User {

}
