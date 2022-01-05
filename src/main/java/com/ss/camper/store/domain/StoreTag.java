package com.ss.camper.store.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
@Table(name = "store_tag", uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(name = "tag_of_store", pkJoinColumns = @PrimaryKeyJoinColumn(name = "store_tag_id"))
public class StoreTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_tag_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false, updatable = false)
    private StoreType storeType;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(table = "tag_of_store", name = "store_id")
    private Long storeId;

}