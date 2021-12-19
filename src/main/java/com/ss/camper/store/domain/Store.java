package com.ss.camper.store.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
@Table(name = "store")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "store_type", columnDefinition = "VARCHAR(30)")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_id")
    protected long id;

    @Column(name = "store_name", length = 100, nullable = false)
    protected String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false, insertable = false, updatable = false)
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

}
