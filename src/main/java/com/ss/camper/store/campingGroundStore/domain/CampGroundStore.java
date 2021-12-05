package com.ss.camper.store.campingGroundStore.domain;

import com.ss.camper.store.domain.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("camp_ground")
@Entity
public class CampGroundStore extends Store {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<CampGroundTag> tags;

}
