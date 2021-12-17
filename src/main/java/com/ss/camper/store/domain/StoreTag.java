package com.ss.camper.store.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "store_tag", uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
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
