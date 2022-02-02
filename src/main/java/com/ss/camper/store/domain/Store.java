package com.ss.camper.store.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString
@Getter
@Builder
@Entity
@Table(name = "store")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_name", length = 100, nullable = false)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false)
    private StoreType storeType;

    @Embedded
    private Address address;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "homepage_url", columnDefinition = "TEXT")
    private String homepageUrl;

    @Column(name = "reservation_url", columnDefinition = "TEXT")
    private String reservationUrl;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date modified;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<StoreTag> tags;

    @Version
    private Long ver;

    public void updateInfo(String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction) {
        this.storeName = storeName;
        this.address = address;
        this.tel = tel;
        this.homepageUrl = homepageUrl;
        this.reservationUrl = reservationUrl;
        this.introduction = introduction;
    }

    public void updateTags(Set<StoreTag> updateTags) {
        this.tags = updateTags;
        if (updateTags != null) {
            StoreTag[] titles = updateTags.toArray(StoreTag[]::new);
            tags.removeIf(tag -> !Arrays.asList(titles).contains(tag));
        }
    }

}
