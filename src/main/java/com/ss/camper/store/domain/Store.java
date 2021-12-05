package com.ss.camper.store.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "store_type", columnDefinition = "VARCHAR(30)")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "store")
public abstract class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "store_name", length = 100, nullable = false)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false, insertable = false, updatable = false)
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

    @Embedded
    private DateRecord dateRecord;

}
