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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "store_type", columnDefinition = "VARCHAR(30)")
public abstract class StoreTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_tag_id")
    protected long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false, insertable = false, updatable = false)
    protected StoreType storeType;

    @Column(name = "title", length = 100, nullable = false)
    protected String title;

}