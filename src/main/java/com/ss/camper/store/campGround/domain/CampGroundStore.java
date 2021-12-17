package com.ss.camper.store.campGround.domain;

import com.ss.camper.store.domain.Address;
import com.ss.camper.store.domain.Store;
import com.ss.camper.store.domain.StoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@DiscriminatorValue("camp_ground")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampGroundStore extends Store {

    public CampGroundStore(long id, String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction) {
        this.id = id;
        this.storeName = storeName;
        this.storeType = StoreType.camp_ground;
        this.address = address;
        this.tel = tel;
        this.homepageUrl = homepageUrl;
        this.reservationUrl = reservationUrl;
        this.introduction = introduction;
    }

    public CampGroundStore(String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction) {
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
