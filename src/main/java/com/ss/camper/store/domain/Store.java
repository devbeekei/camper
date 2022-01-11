package com.ss.camper.store.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
@Getter
@Builder
@Entity
@Table(name = "store")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "store_type", columnDefinition = "VARCHAR(30)")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_id")
    protected long id;

    @Column(name = "store_name", length = 100, nullable = false)
    protected String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false)
    protected StoreType storeType;

    @Embedded
    protected Address address;

    @Column(name = "tel", length = 20)
    protected String tel;

    @Column(name = "homepage_url", columnDefinition = "TEXT")
    protected String homepageUrl;

    @Column(name = "reservation_url", columnDefinition = "TEXT")
    protected String reservationUrl;

    @Column(name = "introduction", columnDefinition = "TEXT")
    protected String introduction;

    @Embedded
    protected DateRecord dateRecord;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<StoreTag> tags;

    public void updateInfo(String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction) {
        this.storeName = storeName;
        this.address = address;
        this.tel = tel;
        this.homepageUrl = homepageUrl;
        this.reservationUrl = reservationUrl;
        this.introduction = introduction;
    }

    public void updateTags(LinkedHashSet<StoreTag> updateTags) {
        this.tags = updateTags;
        if (updateTags != null) {
            StoreTag[] titles = updateTags.toArray(StoreTag[]::new);
            tags.removeIf(tag -> !Arrays.asList(titles).contains(tag));
        }
    }

}
