package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.Store;
import com.ss.camper.store.domain.StoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@SuperBuilder
@Entity
@DiscriminatorValue("camp_ground")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundStore extends Store {

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private LinkedHashSet<CampGroundTag> tags;

    public void addTag(CampGroundTag campGroundTag) {
        if (this.tags == null) this.tags = new LinkedHashSet<>();
        this.tags.add(campGroundTag);
    }

}
