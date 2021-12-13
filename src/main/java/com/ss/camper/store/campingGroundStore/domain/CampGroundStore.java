package com.ss.camper.store.campingGroundStore.domain;

import com.ss.camper.store.domain.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@SuperBuilder
@DiscriminatorValue("camp_ground")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundStore extends Store {

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<CampGroundTag> tags;

    public void addTag(CampGroundTag campGroundTag) {
        if (this.tags == null) this.tags = new HashSet<>();
        this.tags.add(campGroundTag);
    }

}
