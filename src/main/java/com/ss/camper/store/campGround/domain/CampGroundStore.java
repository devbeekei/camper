package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("camp_ground")
public class CampGroundStore extends Store {

    public CampGroundStore(long userId, String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction) {
        this.userId = userId;
        this.storeName = storeName;
        this.address = address;
        this.tel = tel;
        this.homepageUrl = homepageUrl;
        this.reservationUrl = reservationUrl;
        this.introduction = introduction;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<CampGroundTag> tags;

    public void addTag(CampGroundTag campGroundTag) {
        if (this.tags == null) this.tags = new HashSet<>();
        this.tags.add(campGroundTag);
    }

}
