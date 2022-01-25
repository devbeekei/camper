package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@ToString
@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("CLIENT")
@AllArgsConstructor()
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUser extends User {

}
