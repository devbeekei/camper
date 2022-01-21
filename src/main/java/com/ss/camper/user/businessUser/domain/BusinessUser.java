package com.ss.camper.user.businessUser.domain;

import com.ss.camper.store.domain.Store;
import com.ss.camper.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("BUSINESS")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessUser extends User {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "store_of_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> stores;

}
