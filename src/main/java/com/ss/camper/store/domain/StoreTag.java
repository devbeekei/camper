package com.ss.camper.store.domain;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Builder
@Entity
@Table(name = "store_tag", uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}